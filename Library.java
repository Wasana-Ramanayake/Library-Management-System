import java.io.*;
import java.util.*;

class Book {
    String title;
    String author;
    boolean isBorrowed;

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
        this.isBorrowed = false;
    }

    public void borrowBook() {
        isBorrowed = true;
    }

    public void returnBook() {
        isBorrowed = false;
    }

    public String toString() {
        return title + " by " + author + (isBorrowed ? " [Borrowed]" : " [Available]");
    }
}

public class Library {
    static ArrayList<Book> books = new ArrayList<>();
    static final String FILE_NAME = "books.txt";

    public static void main(String[] args) {
        loadBooks();  // Load books from file
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n📚 Library Management System");
            System.out.println("1. Add Book");
            System.out.println("2. View Books");
            System.out.println("3. Borrow Book");
            System.out.println("4. Return Book");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addBook(scanner);
                    break;
                case 2:
                    viewBooks();
                    break;
                case 3:
                    borrowBook(scanner);
                    break;
                case 4:
                    returnBook(scanner);
                    break;
                case 5:
                    saveBooks();
                    System.out.println("📁 Data saved. Exiting...");
                    System.exit(0);
                default:
                    System.out.println("❌ Invalid choice. Try again.");
            }
        }
    }

    // Add a new book
    public static void addBook(Scanner scanner) {
        System.out.print("Enter book title: ");
        String title = scanner.nextLine();
        System.out.print("Enter author name: ");
        String author = scanner.nextLine();
        books.add(new Book(title, author));
        System.out.println("✅ Book added successfully!");
    }

    // View all books
    public static void viewBooks() {
        if (books.isEmpty()) {
            System.out.println("📂 No books available.");
            return;
        }
        System.out.println("\n📖 Book List:");
        for (int i = 0; i < books.size(); i++) {
            System.out.println((i + 1) + ". " + books.get(i));
        }
    }

    // Borrow a book
    public static void borrowBook(Scanner scanner) {
        viewBooks();
        System.out.print("Enter book number to borrow: ");
        int index = scanner.nextInt() - 1;
        if (index >= 0 && index < books.size()) {
            if (!books.get(index).isBorrowed) {
                books.get(index).borrowBook();
                System.out.println("✅ You borrowed: " + books.get(index).title);
            } else {
                System.out.println("❌ This book is already borrowed.");
            }
        } else {
            System.out.println("❌ Invalid book number.");
        }
    }

    // Return a book
    public static void returnBook(Scanner scanner) {
        viewBooks();
        System.out.print("Enter book number to return: ");
        int index = scanner.nextInt() - 1;
        if (index >= 0 && index < books.size()) {
            if (books.get(index).isBorrowed) {
                books.get(index).returnBook();
                System.out.println("✅ You returned: " + books.get(index).title);
            } else {
                System.out.println("❌ This book was not borrowed.");
            }
        } else {
            System.out.println("❌ Invalid book number.");
        }
    }

    // Save books to a file
    public static void saveBooks() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Book book : books) {
                writer.write(book.title + "," + book.author + "," + book.isBorrowed + "\n");
            }
        } catch (IOException e) {
            System.out.println("❌ Error saving books: " + e.getMessage());
        }
    }

    // Load books from a file
    public static void loadBooks() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    Book book = new Book(parts[0], parts[1]);
                    book.isBorrowed = Boolean.parseBoolean(parts[2]);
                    books.add(book);
                }
            }
        } catch (IOException e) {
            System.out.println("❌ Error loading books: " + e.getMessage());
        }
    }
}
