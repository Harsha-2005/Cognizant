/* Exercise 6: Library Management System
 * Scenario: Search for books by title using linear search and binary search.
 *
 * Search Algorithms:
 * -> Linear Search : Scans each element sequentially from the start.
 *                    No sorting required. Time: O(n) worst/average, O(1) best.
 * -> Binary Search : Divides the sorted list in half repeatedly to find the target.
 *                    Requires sorted input. Time: O(log n) worst/average, O(1) best.
 *
 * When to use each:
 * -> Linear Search : Small datasets, unsorted data, or when sorting cost outweighs search frequency.
 * -> Binary Search : Large datasets, pre-sorted data, or when repeated searches are needed.
 *                    For a library with thousands of books sorted by title, Binary Search is ideal. */

import java.util.Arrays;
import java.util.Comparator;

/* Book class with attributes for library searching */
class Book {
    private int bookId;
    private String title;
    private String author;

    public Book(int bookId, String title, String author) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
    }

    public int getBookId() { return bookId; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }

    @Override
    public String toString() {
        return String.format("ID: %-5d | Title: %-35s | Author: %s", bookId, title, author);
    }
}

public class LibraryManagementSystem {

    /* Linear Search: Scans each book's title sequentially until a match is found.
     * Does NOT require the list to be sorted.
     * Time Complexity: Best O(1) | Average O(n) | Worst O(n) */
    public static Book linearSearchByTitle(Book[] books, String targetTitle) {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(targetTitle)) {
                return book;
            }
        }
        return null; // Not found
    }

    /* Binary Search: Assumes books are sorted alphabetically by title.
     * Repeatedly halves the search space to reach the target.
     * Time Complexity: Best O(1) | Average O(log n) | Worst O(log n)
     * Precondition: The books array must be sorted by title. */
    public static Book binarySearchByTitle(Book[] sortedBooks, String targetTitle) {
        int low = 0;
        int high = sortedBooks.length - 1;

        while (low <= high) {
            int mid = (low + high) / 2;
            int cmp = sortedBooks[mid].getTitle().compareToIgnoreCase(targetTitle);

            if (cmp == 0) {
                return sortedBooks[mid]; // Match found
            } else if (cmp < 0) {
                low = mid + 1;           // Target is in the right half
            } else {
                high = mid - 1;          // Target is in the left half
            }
        }
        return null; // Not found
    }

    public static void main(String[] args) {
        Book[] books = {
            new Book(1, "The Great Gatsby",       "F. Scott Fitzgerald"),
            new Book(2, "To Kill a Mockingbird",  "Harper Lee"),
            new Book(3, "1984",                   "George Orwell"),
            new Book(4, "Brave New World",        "Aldous Huxley"),
            new Book(5, "Harry Potter",           "J.K. Rowling"),
            new Book(6, "The Alchemist",          "Paulo Coelho"),
            new Book(7, "Clean Code",             "Robert C. Martin"),
            new Book(8, "Design Patterns",        "Gang of Four"),
            new Book(9, "The Pragmatic Programmer","Andy Hunt"),
            new Book(10,"Dune",                   "Frank Herbert")
        };

        // Sorted copy for Binary Search (sorted alphabetically by title)
        Book[] sortedBooks = books.clone();
        Arrays.sort(sortedBooks, Comparator.comparing(b -> b.getTitle().toLowerCase()));

        System.out.println("=== Library Management System Demo ===\n");

        // ---- Linear Search ----
        System.out.println("--- Linear Search ---");
        String linearTarget = "Harry Potter";
        Book linearResult = linearSearchByTitle(books, linearTarget);
        System.out.println("Searching for: \"" + linearTarget + "\"");
        System.out.println(linearResult != null ? "Found: " + linearResult : "Book not found.");

        String linearMissing = "Sherlock Holmes";
        Book linearMissResult = linearSearchByTitle(books, linearMissing);
        System.out.println("\nSearching for: \"" + linearMissing + "\"");
        System.out.println(linearMissResult != null ? "Found: " + linearMissResult : "Book not found.");

        // ---- Binary Search ----
        System.out.println("\n--- Binary Search (sorted by title) ---");
        System.out.println("Sorted book list:");
        for (Book b : sortedBooks) {
            System.out.println("  " + b);
        }

        String binaryTarget = "Clean Code";
        Book binaryResult = binarySearchByTitle(sortedBooks, binaryTarget);
        System.out.println("\nSearching for: \"" + binaryTarget + "\"");
        System.out.println(binaryResult != null ? "Found: " + binaryResult : "Book not found.");

        String binaryMissing = "Sherlock Holmes";
        Book binaryMissResult = binarySearchByTitle(sortedBooks, binaryMissing);
        System.out.println("\nSearching for: \"" + binaryMissing + "\"");
        System.out.println(binaryMissResult != null ? "Found: " + binaryMissResult : "Book not found.");
    }
}

// -------- Analysis --------

/* Time Complexity Comparison:
 * ┌──────────────────┬────────────┬──────────────┬────────────┬────────────────────┐
 * │ Algorithm        │ Best Case  │ Average Case │ Worst Case │ Sorted Required?   │
 * ├──────────────────┼────────────┼──────────────┼────────────┼────────────────────┤
 * │ Linear Search    │   O(1)     │    O(n)      │   O(n)     │       No           │
 * │ Binary Search    │   O(1)     │  O(log n)    │  O(log n)  │      YES           │
 * └──────────────────┴────────────┴──────────────┴────────────┴────────────────────┘
 *
 * Practical Guidelines for Library System:
 * -> Use Linear Search when:
 *    - The book catalog is small (< 100 books).
 *    - Books are not sorted, and sorting cost is not worth it.
 *    - A one-time search is needed.
 *
 * -> Use Binary Search when:
 *    - The book catalog is large (thousands of books).
 *    - The catalog is already sorted or can be maintained in sorted order.
 *    - Frequent repeated searches are expected (e.g., daily user queries).
 *    - For a 10,000-book library, Binary Search takes at most ~14 comparisons vs 10,000 for Linear Search.
 */
