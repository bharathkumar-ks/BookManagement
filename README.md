Workflow (BookService)

Start Application

Run BookMain.

Menu is shown in console (Add Book, Register Member, Borrow, Return, Search, Reports, Exit).

Add Book

User enters: Book ID, Title, Author, Total Copies.

System checks: Book ID must be unique.

Book is stored in memory (ArrayList).

availableCopies is set equal to totalCopies.

Register Member

User enters: Member ID, Name.

System checks: Member ID must be unique.

Member is stored in memory (ArrayList).

Borrow Book

User enters: Member ID, Book ID.

System checks:

Member exists

Book exists

Member has borrowed less than 3 active books

Member has not already borrowed the same book (active)

Book has availableCopies > 0

If valid:

Create a BorrowRecord with borrowDate = today, dueDate = today + 14 days

Store BorrowRecord in memory

Decrease book availableCopies by 1

Return Book

User enters: Borrow Record ID

System checks:

Borrow record exists

Book is not already returned

If valid:

Set returnDate = today

Increase book availableCopies by 1

If returnDate is after dueDate:

Calculate overdue days

Fine = overdueDays × Rs.2

Create Fine object (optional) and show fine amount

Search Book

User enters a keyword.

System searches in-memory books list for partial matches in:

Title

Author

Matching books are displayed.

Member Report

User enters: Member ID

System displays all active borrowed books of that member:

Book ID

Borrow Date

Due Date

Book Report

User enters: Book ID

System displays:

Book availability (availableCopies/totalCopies)

Members who currently borrowed the book (if implemented in report service)

Top Borrowers

System counts total borrow records per member (including returned history).

Sort members by total borrowed count (descending).

Display the ranked list.

Exit

Program stops (all data is lost because it is in-memory only).
