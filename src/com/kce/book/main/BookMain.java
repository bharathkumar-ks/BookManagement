package com.kce.book.main;
import java.util.ArrayList;
import java.util.Scanner;
import com.kce.book.entity.Book;
import com.kce.book.entity.Fine;
import com.kce.book.repository.BookRepository;
import com.kce.book.repository.MemberRepository;
import com.kce.book.repository.BorrowRepository;
import com.kce.book.service.LibraryService;
import com.kce.book.service.BorrowService;
import com.kce.book.service.SearchService;
import com.kce.book.service.ReportService;

public class BookMain {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        BookRepository bookRepo = new BookRepository();
        MemberRepository memberRepo = new MemberRepository();
        BorrowRepository borrowRepo = new BorrowRepository();
        LibraryService libraryService = new LibraryService(bookRepo, memberRepo);
        BorrowService borrowService = new BorrowService(bookRepo, memberRepo, borrowRepo);
        SearchService searchService = new SearchService(bookRepo);
        ReportService reportService = new ReportService(bookRepo, memberRepo, borrowRepo);
        while (true) {
            System.out.println("\n===== Library Management System =====");
            System.out.println("1. Add Book");
            System.out.println("2. Register Member");
            System.out.println("3. Borrow Book");
            System.out.println("4. Return Book");
            System.out.println("5. Search Book");
            System.out.println("6. Member Report");
            System.out.println("7. Book Report");
            System.out.println("8. Top Borrowers");
            System.out.println("9. Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    System.out.print("Enter Book ID: ");
                    String bookId = sc.nextLine();
                    System.out.print("Enter Title: ");
                    String title = sc.nextLine();
                    System.out.print("Enter Author: ");
                    String author = sc.nextLine();
                    System.out.print("Enter Total Copies: ");
                    int copies = sc.nextInt();
                    sc.nextLine();
                    String bookResult = libraryService.addBook(bookId, title, author, copies);
                    System.out.println(bookResult);
                    break;

                case 2:
                    System.out.print("Enter Member ID: ");
                    String memberId = sc.nextLine();
                    System.out.print("Enter Member Name: ");
                    String name = sc.nextLine();
                    String memberResult = libraryService.registerMember(memberId, name);
                    System.out.println(memberResult);
                    break;

                case 3:
                    System.out.print("Enter Member ID: ");
                    String mId = sc.nextLine();
                    System.out.print("Enter Book ID: ");
                    String bId = sc.nextLine();
                    String borrowResult = borrowService.borrowBook(mId, bId);
                    System.out.println(borrowResult);
                    break;

                case 4:
                    System.out.print("Enter Borrow Record ID: ");
                    String recordId = sc.nextLine();
                    Object result = borrowService.returnBook(recordId);
                    if (result instanceof Fine) {
                        Fine f = (Fine) result;
                        System.out.println("Book returned with fine: Rs." + f.getFineAmount());
                    } else {
                        System.out.println(result);
                    }
                    break;

                case 5:
                    System.out.print("Enter keyword (title/author): ");
                    String keyword = sc.nextLine();
                    ArrayList<Book> books = searchService.searchByTitleOrAuthor(keyword);
                    if (books.isEmpty()) {
                        System.out.println("No books found.");
                    } else {
                        for (Book b : books) {
                            System.out.println(
                                    b.getBookId() + " | " +
                                    b.getTitle() + " | " +
                                    b.getAuthor() + " | Available: " +
                                    b.getAvailableCopies());
                        }
                    }
                    break;

                case 6:
                    System.out.print("Enter Member ID: ");
                    String memId = sc.nextLine();
                    var list = reportService.memberReport(memId);
                    if (list.isEmpty()) {
                        System.out.println("No borrowed books.");
                    } else {
                        list.forEach(r ->
                                System.out.println("Book ID: " + r.getBookId() +
                                        " | Borrowed: " + r.getBorrowDate() +
                                        " | Due: " + r.getDueDate()));
                    }
                    break;

                case 7:
                    System.out.print("Enter Book ID: ");
                    String bookReportId = sc.nextLine();
                    System.out.println(reportService.bookAvailabilityStatus(bookReportId));
                    break;

                case 8:
                    var top = reportService.topBorrowers();
                    if (top.isEmpty()) {
                        System.out.println("No data.");
                    } else {
                        top.forEach(System.out::println);
                    }
                    break;

                case 9:
                    System.out.println("Exiting system...");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}