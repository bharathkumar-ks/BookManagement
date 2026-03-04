package com.kce.book.service;
import java.time.LocalDate;
import java.util.ArrayList;
import com.kce.book.entity.Book;
import com.kce.book.entity.BorrowRecord;
import com.kce.book.entity.Fine;
import com.kce.book.repository.BookRepository;
import com.kce.book.repository.BorrowRepository;
import com.kce.book.repository.MemberRepository;

public class BorrowService {
    private BookRepository bookRepo;
    private MemberRepository memberRepo;
    private BorrowRepository borrowRepo;
    private static final int MAX_BORROW = 3;
    private static final int DUE_DAYS = 14;
    private static final int FINE_PER_DAY = 2;
    private int recordCounter = 1;
    private int fineCounter = 1;

    public BorrowService(BookRepository bookRepo, MemberRepository memberRepo, BorrowRepository borrowRepo) {
        this.bookRepo = bookRepo;
        this.memberRepo = memberRepo;
        this.borrowRepo = borrowRepo;
    }
    private int activeBorrowCount(String memberId) {
        int count = 0;
        for (BorrowRecord r : borrowRepo.getAllBorrowRecords()) {
            if (r.getMemberId().equals(memberId) && r.getReturnDate() == null) {
                count++;
            }
        }
        return count;
    }
    private boolean alreadyBorrowedSameBook(String memberId, String bookId) {
        for (BorrowRecord r : borrowRepo.getAllBorrowRecords()) {
            if (r.getMemberId().equals(memberId) &&
                r.getBookId().equals(bookId) &&
                r.getReturnDate() == null) {
                return true;
            }
        }
        return false;
    }

    public String borrowBook(String memberId, String bookId) {
        if (memberRepo.findMemberById(memberId) == null) return "Member not found!";
        Book book = bookRepo.findBookById(bookId);
        if (book == null) return "Book not found!";
        if (activeBorrowCount(memberId) >= MAX_BORROW) {
            return "Member already borrowed maximum (3) books!";
        }
        if (alreadyBorrowedSameBook(memberId, bookId)) {
            return "Member cannot borrow the same book twice at the same time!";
        }
        if (book.getAvailableCopies() <= 0) {
            return "No copies available for this book!";
        }
        LocalDate borrowDate = LocalDate.now();
        LocalDate dueDate = borrowDate.plusDays(DUE_DAYS);
        String recordId = "R" + recordCounter++;
        BorrowRecord record = new BorrowRecord(recordId, memberId, bookId, borrowDate, dueDate);
        borrowRepo.addBorrowRecord(record);
        book.setAvailableCopies(book.getAvailableCopies() - 1);
        return "Borrowed successfully! Record ID: " + recordId + " | Due Date: " + dueDate;
    }

    public Object returnBook(String recordId) {
        BorrowRecord record = borrowRepo.findRecordById(recordId);
        if (record == null) return "Borrow record not found!";
        if (record.getReturnDate() != null) return "This book is already returned!";
        Book book = bookRepo.findBookById(record.getBookId());
        if (book == null) return "Book not found (data error)!";
        LocalDate returnDate = LocalDate.now();
        record.setReturnDate(returnDate);
        book.setAvailableCopies(book.getAvailableCopies() + 1);
        long overdueDays = 0;
        if (returnDate.isAfter(record.getDueDate())) {
            overdueDays = record.getDueDate().until(returnDate).getDays();
        }
        int fineAmount = (int) overdueDays * FINE_PER_DAY;
        if (fineAmount > 0) {
            String fineId = "F" + fineCounter++;
            Fine fine = new Fine(fineId, record.getMemberId(), record.getRecordId(), fineAmount);
            return fine; 
        }

        return "Returned successfully! No fine.";
    }

    public ArrayList<BorrowRecord> getAllBorrowRecords() {
        return borrowRepo.getAllBorrowRecords();
    }
}