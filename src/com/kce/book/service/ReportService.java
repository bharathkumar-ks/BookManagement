package com.kce.book.service;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import com.kce.book.entity.Book;
import com.kce.book.entity.BorrowRecord;
import com.kce.book.entity.Member;
import com.kce.book.repository.BookRepository;
import com.kce.book.repository.BorrowRepository;
import com.kce.book.repository.MemberRepository;

public class ReportService {
    private BookRepository bookRepo;
    private MemberRepository memberRepo;
    private BorrowRepository borrowRepo;
    public ReportService(BookRepository bookRepo, MemberRepository memberRepo, BorrowRepository borrowRepo) {
        this.bookRepo = bookRepo;
        this.memberRepo = memberRepo;
        this.borrowRepo = borrowRepo;
    }
    public ArrayList<BorrowRecord> memberReport(String memberId) {
        ArrayList<BorrowRecord> list = new ArrayList<>();
        for (BorrowRecord r : borrowRepo.getAllBorrowRecords()) {
            if (r.getMemberId().equals(memberId) && r.getReturnDate() == null) {
                list.add(r);
            }
        }
        return list;
    }
    
    public String bookAvailabilityStatus(String bookId) {
        Book b = bookRepo.findBookById(bookId);
        if (b == null) return "Book not found!";
        return "Book: " + b.getTitle() +
               " | Available: " + b.getAvailableCopies() + "/" + b.getTotalCopies();
    }

    public ArrayList<String> whoBorrowedBookNow(String bookId) {
        ArrayList<String> memberNames = new ArrayList<>();
        for (BorrowRecord r : borrowRepo.getAllBorrowRecords()) {
            if (r.getBookId().equals(bookId) && r.getReturnDate() == null) {
                Member m = memberRepo.findMemberById(r.getMemberId());
                if (m != null) memberNames.add(m.getMemberId() + " - " + m.getName());
                else memberNames.add(r.getMemberId());
            }
        }
        return memberNames;
    }

    public ArrayList<String> topBorrowers() {
        ArrayList<Member> members = memberRepo.getAllMembers();
        ArrayList<String> result = new ArrayList<>();
        ArrayList<MemberCount> counts = new ArrayList<>();
        for (Member m : members) {
            int total = 0;
            for (BorrowRecord r : borrowRepo.getAllBorrowRecords()) {
                if (r.getMemberId().equals(m.getMemberId())) total++;
            }
            counts.add(new MemberCount(m.getMemberId(), m.getName(), total));
        }

        Collections.sort(counts, new Comparator<MemberCount>() {
            @Override
            public int compare(MemberCount a, MemberCount b) {
                return Integer.compare(b.totalBorrowed, a.totalBorrowed);
            }
        });
        for (MemberCount mc : counts) {
            result.add(mc.memberId + " - " + mc.name + " | Total Borrowed: " + mc.totalBorrowed);
        }
        return result;
    }

    private static class MemberCount {
        String memberId;
        String name;
        int totalBorrowed;
        MemberCount(String memberId, String name, int totalBorrowed) {
            this.memberId = memberId;
            this.name = name;
            this.totalBorrowed = totalBorrowed;
        }
    }
}