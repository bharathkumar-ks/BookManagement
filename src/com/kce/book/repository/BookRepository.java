package com.kce.book.repository;
import java.util.ArrayList;
import com.kce.book.entity.Book;

public class BookRepository {
    private ArrayList<Book> books = new ArrayList<>();
    public void addBook(Book book) {
        books.add(book);
    }
    public Book findBookById(String bookId) {
        for (Book b : books) {
            if (b.getBookId().equals(bookId)) {
                return b;
            }
        }
        return null;
    }
    public ArrayList<Book> getAllBooks() {
        return books;
    }
}