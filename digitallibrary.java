import java.util.*;

class digitallibrary {
    private static Map<String, User> users = new HashMap<>();
    private static Map<String, Book> books = new HashMap<>();
    private static User currentUser = null;

    static {
        // Adding some default users (admin and normal user)
        users.put("admin", new User("admin", "admin123", true));
        users.put("user1", new User("user1", "password1", false));

        // Adding some default books
        books.put("B001", new Book("B001", "The Great Gatsby", "F. Scott Fitzgerald"));
        books.put("B002", new Book("B002", "1984", "George Orwell"));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Digital Library Management System");

        while (true) {
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    login(scanner);
                    break;
                case 2:
                    register(scanner);
                    break;
                case 3:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void login(Scanner scanner) {
        System.out.print("Enter login ID: ");
        String loginId = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if (authenticate(loginId, password)) {
            System.out.println("Login successful!");
            currentUser = users.get(loginId);

            while (currentUser != null) {
                if (currentUser.isAdmin()) {
                    adminMenu(scanner);
                } else {
                    userMenu(scanner);
                }
            }
        } else {
            System.out.println("Invalid login ID or password.");
        }
    }

    private static void register(Scanner scanner) {
        System.out.print("Enter new login ID: ");
        String newLoginId = scanner.nextLine();
        System.out.print("Enter new password: ");
        String newPassword = scanner.nextLine();

        if (users.containsKey(newLoginId)) {
            System.out.println("Login ID already exists. Please choose a different login ID.");
        } else {
            users.put(newLoginId, new User(newLoginId, newPassword, false));
            System.out.println("Registration successful! You can now log in with your new credentials.");
        }
    }

    private static boolean authenticate(String loginId, String password) {
        return users.containsKey(loginId) && users.get(loginId).getPassword().equals(password);
    }

    private static void adminMenu(Scanner scanner) {
        System.out.println("Admin Menu:");
        System.out.println("1. Add Book");
        System.out.println("2. Update Book");
        System.out.println("3. Delete Book");
        System.out.println("4. View Books");
        System.out.println("5. View Issued Books");
        System.out.println("6. Logout");
        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                addBook(scanner);
                break;
            case 2:
                updateBook(scanner);
                break;
            case 3:
                deleteBook(scanner);
                break;
            case 4:
                viewBooks();
                break;
            case 5:
                viewIssuedBooks();
                break;
            case 6:
                System.out.println("Logging out...");
                currentUser = null;
                return;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private static void userMenu(Scanner scanner) {
        System.out.println("User Menu:");
        System.out.println("1. View Books");
        System.out.println("2. Issue Book");
        System.out.println("3. Return Book");
        System.out.println("4. View Issued Books");
        System.out.println("5. Logout");
        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                viewBooks();
                break;
            case 2:
                issueBook(scanner);
                break;
            case 3:
                returnBook(scanner);
                break;
            case 4:
                viewIssuedBooks();
                break;
            case 5:
                System.out.println("Logging out...");
                currentUser = null;
                return;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private static void addBook(Scanner scanner) {
        System.out.print("Enter book ID: ");
        String bookId = scanner.nextLine();
        System.out.print("Enter book title: ");
        String title = scanner.nextLine();
        System.out.print("Enter book author: ");
        String author = scanner.nextLine();

        books.put(bookId, new Book(bookId, title, author));
        System.out.println("Book added successfully!");
    }

    private static void updateBook(Scanner scanner) {
        System.out.print("Enter book ID to update: ");
        String bookId = scanner.nextLine();

        if (books.containsKey(bookId)) {
            System.out.print("Enter new book title: ");
            String title = scanner.nextLine();
            System.out.print("Enter new book author: ");
            String author = scanner.nextLine();

            books.put(bookId, new Book(bookId, title, author));
            System.out.println("Book updated successfully!");
        } else {
            System.out.println("Book not found.");
        }
    }

    private static void deleteBook(Scanner scanner) {
        System.out.print("Enter book ID to delete: ");
        String bookId = scanner.nextLine();

        if (books.containsKey(bookId)) {
            books.remove(bookId);
            System.out.println("Book deleted successfully!");
        } else {
            System.out.println("Book not found.");
        }
    }

    private static void viewBooks() {
        System.out.println("Books in the library:");
        for (Book book : books.values()) {
            System.out.println(book);
        }
    }

    private static void viewIssuedBooks() {
        boolean hasIssuedBooks = false;
        System.out.println("Issued Books:");
        for (Book book : books.values()) {
            if (book.isIssued()) {
                System.out.println(book);
                hasIssuedBooks = true;
            }
        }
        if (!hasIssuedBooks) {
            System.out.println("No books issued.");
        }
    }

    private static void issueBook(Scanner scanner) {
        System.out.print("Enter book ID to issue: ");
        String bookId = scanner.nextLine();

        if (books.containsKey(bookId)) {
            Book book = books.get(bookId);
            if (book.isIssued()) {
                System.out.println("Book is already issued.");
            } else {
                book.setIssued(true);
                System.out.println("Book issued successfully!");
            }
        } else {
            System.out.println("Book not found.");
        }
    }

    private static void returnBook(Scanner scanner) {
        System.out.print("Enter book ID to return: ");
        String bookId = scanner.nextLine();

        if (books.containsKey(bookId)) {
            Book book = books.get(bookId);
            if (!book.isIssued()) {
                System.out.println("Book is not issued.");
            } else {
                book.setIssued(false);
                System.out.println("Book returned successfully!");
            }
        } else {
            System.out.println("Book not found.");
        }
    }
}

class User {
    private String password;
    private boolean isAdmin;

    public User(String loginId, String password, boolean isAdmin) {
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public String getPassword() {
        return password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }
}

class Book {
    private String bookId;
    private String title;
    private String author;
    private boolean isIssued;

    public Book(String bookId, String title, String author) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.isIssued = false;
    }

    public boolean isIssued() {
        return isIssued;
    }

    public void setIssued(boolean issued) {
        isIssued = issued;
    }

    @Override
    public String toString() {
        return "Book ID: " + bookId + ", Title: " + title + ", Author: " + author + ", Issued: " + isIssued;
    }
}