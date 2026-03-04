package com.kce.book.service;
import java.util.ArrayList;
import com.kce.book.entity.Book;
import com.kce.book.repository.BookRepository;

public class SearchService {
    private BookRepository bookRepo;
    public SearchService(BookRepository bookRepo) {
        this.bookRepo = bookRepo;
    }

    public ArrayList<Book> searchByTitleOrAuthor(String keyword) {
        ArrayList<Book> result = new ArrayList<>();
        if (keyword == null) return result;
        String key = keyword.trim().toLowerCase();
        for (Book b : bookRepo.getAllBooks()) {
            if (b.getTitle().toLowerCase().contains(key) ||
                b.getAuthor().toLowerCase().contains(key)) {
                result.add(b);
            }
        }
        return result;
    }
}