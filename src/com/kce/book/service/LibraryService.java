package com.kce.book.service;
import com.kce.book.entity.Book;
import com.kce.book.entity.Member;
import com.kce.book.repository.BookRepository;
import com.kce.book.repository.MemberRepository;

public class LibraryService {
    private BookRepository bookRepo;
    private MemberRepository memberRepo;
    public LibraryService(BookRepository bookRepo, MemberRepository memberRepo) {
        this.bookRepo = bookRepo;
        this.memberRepo = memberRepo;
    }

    public String addBook(String bookId, String title, String author, int copies) {
        if (bookId == null || bookId.trim().isEmpty()) return "Book ID cannot be empty!";
        if (title == null || title.trim().isEmpty()) return "Title cannot be empty!";
        if (author == null || author.trim().isEmpty()) return "Author cannot be empty!";
        if (copies <= 0) return "Copies must be greater than 0!";
        if (bookRepo.findBookById(bookId) != null) {
            return "Book ID already exists!";
        }
        Book b = new Book(bookId.trim(), title.trim(), author.trim(), copies);
        bookRepo.addBook(b);
        return "Book added successfully!";
    }

    public String registerMember(String memberId, String name) {
        if (memberId == null || memberId.trim().isEmpty()) return "Member ID cannot be empty!";
        if (name == null || name.trim().isEmpty()) return "Name cannot be empty!";
        if (memberRepo.findMemberById(memberId) != null) {
            return "Member ID already exists!";
        }
        Member m = new Member(memberId.trim(), name.trim());
        memberRepo.addMember(m);
        return "Member registered successfully!";
    }
}