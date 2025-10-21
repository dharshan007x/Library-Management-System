import java.sql.*;
import java.util.*;

public class LibraryManagement {
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n=== Library Management System ===");
            System.out.println("1. Add Book");
            System.out.println("2. View Books");
            System.out.println("3. Issue Book");
            System.out.println("4. Return Book");
            System.out.println("5. Delete Book");
            System.out.println("6. Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> addBook();
                case 2 -> viewBooks();
                case 3 -> issueBook();
                case 4 -> returnBook();
                case 5 -> deleteBook();
                case 6 -> {
                    System.out.println("Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    static void addBook() {
        System.out.print("Enter book ID: ");
        int id = sc.nextInt();
        sc.nextLine(); // consume newline
        System.out.print("Enter title: ");
        String title = sc.nextLine();
        System.out.print("Enter author: ");
        String author = sc.nextLine();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     "INSERT INTO books (id, title, author, available) VALUES (?, ?, ?, true)")) {
            ps.setInt(1, id);
            ps.setString(2, title);
            ps.setString(3, author);
            ps.executeUpdate();
            System.out.println("✅ Book added successfully!");
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("❌ Error: Book ID already exists. Try a different ID.");
        } catch (Exception e) {
            System.out.println("Database error: " + e);
        }
    }

    static void viewBooks() {
        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM books")) {
            System.out.println("\nID | Title | Author | Available");
            System.out.println("------------------------------------");
            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                boolean available = rs.getBoolean("available");
                System.out.printf("%d | %s | %s | %s%n", id, title, author, available ? "Yes" : "No");
            }
        } catch (Exception e) {
            System.out.println("Database error: " + e);
        }
    }

    static void issueBook() {
        System.out.print("Enter book ID to issue: ");
        int id = sc.nextInt();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     "UPDATE books SET available=false WHERE id=?")) {
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            if (rows > 0)
                System.out.println("📚 Book issued successfully!");
            else
                System.out.println("❌ No book found with that ID.");
        } catch (Exception e) {
            System.out.println("Database error: " + e);
        }
    }

    static void returnBook() {
        System.out.print("Enter book ID to return: ");
        int id = sc.nextInt();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     "UPDATE books SET available=true WHERE id=?")) {
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            if (rows > 0)
                System.out.println("✅ Book returned successfully!");
            else
                System.out.println("❌ No book found with that ID.");
        } catch (Exception e) {
            System.out.println("Database error: " + e);
        }
    }

    static void deleteBook() {
        System.out.print("Enter book ID to delete: ");
        int id = sc.nextInt();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     "DELETE FROM books WHERE id=?")) {
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            if (rows > 0)
                System.out.println("🗑️ Book deleted successfully!");
            else
                System.out.println("❌ No book found with that ID.");
        } catch (Exception e) {
            System.out.println("Database error: " + e);
        }
    }
}
