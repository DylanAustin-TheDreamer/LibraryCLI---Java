package org.example.Application;

import com.opencsv.exceptions.CsvException;
import org.example.Books.Book;
import org.example.Database.Database;
import org.example.User.Admin;
import org.example.User.User;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import static org.example.Database.DatabaseUtilities.*;

public class Application {
    Database database;
    Database userDatabase;
    List<Book> books;
    private String filePath = "src/main/java/org/example/User/users_data.csv";
    private User user = null;
    private Admin admin = null;
    public String scannerInput;

    public Application(Database database, Database userDatabase) {
        this.database = database;
        this.userDatabase = userDatabase;
        this.books = database.getDatabase();
    }

    public void startApplication() {
        System.out.println("---");

        // check account
        if (user != null) {
            System.out.println("User: " + user.getName());
            System.out.println("Press *: User Account");
            System.out.println(" ");
        } else if (admin != null){
            System.out.println("Admin: " + admin.getName());
            System.out.println(" ");
        } else {
            System.out.println("Press 1: Make Account");
            System.out.println("Press 2: logIntoAccount");
        }
        // -------------

        System.out.println("Press 3: Library");
        System.out.println("Press 4: Exit");
        Scanner scanner = new Scanner(System.in);
        scannerInput = scanner.nextLine();
        generatePath(String.valueOf(scannerInput));
    }
    public void makeAccount() {
        int adminCode = 6767;
        System.out.println("---");
        System.out.println("Press 1: Make User");
        System.out.println("Press 2: Make Admin");
        System.out.println("Press 3: Back");
        Scanner scanner = new Scanner(System.in);
        scannerInput = scanner.nextLine();

        if (String.valueOf(scannerInput).equals("1")) {
            System.out.println("Enter new name: ");
            Scanner scannerName = new Scanner(System.in);
            scannerInput = scannerName.nextLine();
            String newUsername = scannerInput;
            System.out.println("Enter password: ");
            scannerInput = scannerName.nextLine();
            String newPassword = scannerInput;
            this.user = new User(String.valueOf(newUsername), String.valueOf(newPassword), null, false);
            try {
                saveUsers(filePath, this.user.getName(), this.user.getPassword(), null);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            this.startApplication();
        } else if (String.valueOf(scannerInput).equals("2")) {
            System.out.println("Enter Admin Code: ");
            Scanner scannerCode = new Scanner(System.in);
            scannerInput = scannerCode.nextLine();
            String convert = String.valueOf(scannerInput);
            if (Integer.valueOf(convert).equals(adminCode)) {
                System.out.println("Enter new name: ");
                Scanner scannerName = new Scanner(System.in);
                scannerInput = scannerName.nextLine();
                this.admin = new Admin(String.valueOf(scannerInput));
                this.startApplication();
            } else {
                System.out.println("Invalid Admin Code! - Taking you back to lobby... ");
                this.startApplication();
            }
        } else this.startApplication();
    }
    public void logIntoAccount() {
        // Get database of users available from database utilities
        System.out.println("Enter Username: ");
        Scanner scannerName = new Scanner(System.in);
        scannerInput = scannerName.nextLine();
        String username = scannerInput;
        System.out.println("Enter Password: ");
        scannerInput = scannerName.nextLine();
        String password = scannerInput;
        for (User users : this.userDatabase.getUserDatabase()) {
            if(username.toLowerCase().equals(users.getName().toLowerCase()) && password.toLowerCase().equals(users.getPassword().toLowerCase())) {
                this.user = new User(users.getName(), users.getPassword(), users.getBookIndex(), false);
                this.startApplication();
            }
        }
        System.out.println("No records matched... taking you back...");
        this.startApplication();

    }
    public void Library() {
        System.out.println("---");

        // check account
        if (user != null) {
            System.out.println("User: " + user.getName());
        } else if (admin != null){
            System.out.println("Admin: " + admin.getName());
        }
        // -------------

        System.out.println(" ");
        if (admin != null) {
            System.out.println("Press *: Admin tools");
        }
        System.out.println("Press 1: Search by title");
        System.out.println("Press 2: Search by index");
        System.out.println("Press 3: Browse all books");
        System.out.println("Press 4: Back");
        Scanner scanner = new Scanner(System.in);
        scannerInput = scanner.nextLine();
        librarySearch(String.valueOf(scannerInput));
    }
    public void generatePath(String input) {
        switch(input) {
            case "1":
                if (user == null && admin == null) {
                    this.makeAccount();
                } else {
                    System.out.println("---");
                    System.out.println("Refer to options and try again...");
                    this.startApplication();
                }
                // code block
                break;
            case "2":
                this.logIntoAccount();
                // code block
                break;
            case "3":
                // code block
                this.Library();
                break;
            case "4":
                // code block
                this.Exit();
                break;
            case "*":
                // code block
                UserDashboard();
                break;
            default:
                // code block
                System.out.println("---");
                System.out.println("Refer to options and try again...");
                this.startApplication();
        }
    }
    public void librarySearch(String input) {

        switch (input){
            case "1":
                // code block
                System.out.println("---");
                System.out.println("Press x: Back");
                System.out.println("Type the title and press enter...");
                Scanner scannerTitle = new Scanner(System.in);
                scannerInput = scannerTitle.nextLine();
                String title = String.valueOf(scannerInput);
                Book returnedTitleBook = this.database.searchTitle(title);
                if (Objects.equals(title, "x") || Objects.equals(title, "X")) {
                    Library();
                } else if (!Objects.equals(title, "x") || !Objects.equals(title, "X")) {
                    if (returnedTitleBook == null) librarySearch("1");
                }
                System.out.println(returnedTitleBook.getBookDetails());
                System.out.println(" ");
                if(!returnedTitleBook.isLoaned()) {
                    if (user != null) {
                        System.out.println("Press 1: Loan");
                    } else System.out.println("If you wish to loan, you must log in");
                } else System.out.println("This book is already out on loan");
                // --------------------------------

                System.out.println("Press 2: Back to library");
                scannerInput = scannerTitle.nextLine();
                if (String.valueOf(scannerInput).equals("1") && !returnedTitleBook.isLoaned()) {
                    if (user != null) {
                        System.out.println(" ");
                        this.database.setDatabaseBookToLoan(Integer.parseInt(String.valueOf(returnedTitleBook.getIndex())), user.getName());
                        try {
                            updateExistingUsers(user.getName(), returnedTitleBook.getIndex());
                            this.user.setBookIndex(String.valueOf(returnedTitleBook.getIndex()));
                            Library();
                        } catch (IOException | CsvException e){
                            throw new RuntimeException(e);
                        }
                    } else this.Library();
                } else {
                    this.Library();
                }
                break;
            case "2":
                // code block
                System.out.println("---");
                System.out.println("Index from 1 to " + books.getLast().getIndex());
                System.out.println("Press x: Back");
                System.out.println("Type the book ID index and press enter...");
                Scanner scannerIndex = new Scanner(System.in);
                scannerInput = scannerIndex.nextLine();
                String convert = String.valueOf(scannerInput);
                Book returnedIndexBook = this.database.getSpecific(convert);
                if (Objects.equals(convert, "x") || Objects.equals(convert, "X")) {
                    Library();
                } else if (!Objects.equals(convert, "x") || !Objects.equals(convert, "X")) {
                    if (returnedIndexBook == null) librarySearch("2");
                }
                System.out.println(returnedIndexBook.getBookDetails());
                System.out.println(" ");
                if(!returnedIndexBook.isLoaned()) {
                    if (user != null) {
                        System.out.println("Press 1: Loan");
                    } else System.out.println("If you wish to loan, you must log in");
                } else System.out.println("This book is already out on loan");
                System.out.println("Press 2: Back to library");
                scannerInput = scannerIndex.nextLine();
                if (String.valueOf(scannerInput).equals("1") && !returnedIndexBook.isLoaned()) {
                    if (user != null) {
                        System.out.println(" ");
                        this.database.setDatabaseBookToLoan(returnedIndexBook.getIndex(), user.getName());
                        try {
                            updateExistingUsers(user.getName(), returnedIndexBook.getIndex());
                            this.user.setBookIndex(String.valueOf(returnedIndexBook.getIndex()));
                            Library();
                        } catch (IOException | CsvException e){
                            throw new RuntimeException(e);
                        }
                    } else this.Library();
                } else {
                    this.Library();
                }

                break;
            case "3":
                // code block
                for (Book books : this.database.getDatabase()){
                    System.out.println("---");
                    System.out.println("Press Enter: Move through collection");
                    System.out.println("Press x: Back to library");
                    System.out.println(" ");
                    System.out.println(books.getBookDetails());
                    Scanner scanner = new Scanner(System.in);
                    scannerInput = scanner.nextLine();
                    if (String.valueOf(scannerInput).equals("x") || String.valueOf(scannerInput).equals("X")){
                        this.Library();
                    }
                }
                this.Library();
                break;
            case "4":
                // code block
                this.startApplication();
                break;
            case "*":
                // code block
                if (admin != null){
                    AdminDashboard();
                }
                break;
            default:
                // code block
                System.out.println("---");
                System.out.println("Refer to options and try again...");
                this.startApplication();
        }
    }
    // Extra details for users and Admin
    public void UserDashboard() {
        System.out.println("---");
        System.out.println("Welcome " + user.getName());
        System.out.println(" ");
        System.out.println("Books you have loaned (only one book can be loaned whilst we improve our services): ");
        String index = user.getBookIndex();
        Book book = database.getSpecific(index);
        if (book != null) {
            System.out.println(book.getBookDetails());
        } else System.out.println("You do not currently have any books on loan");
        System.out.println(" ");
        System.out.println("Press Enter: Back");
        Scanner scanner = new Scanner(System.in);
        scannerInput = scanner.nextLine();
        startApplication();
    }
    public void AdminDashboard() {
        System.out.println("---");
        System.out.println("Okay volunteer, we got some labor for you that no one else wants to do...");
        System.out.println("We need you to go through every single book in the database, and check which books are loaned out and which are not");
        System.out.println("After which, we need you to write down a log of every book that is out on loan, and how many times these books have been loaned");
        System.out.println("If you need any further guidance, please refer to the worker's hand book or email HR to raise your concerns");
        System.out.println("We're.... currently busy... We trust you will be fine on your own...");
        System.out.println(" ");
        System.out.println("Press 1: Go through 120 books");
        Scanner scanner = new Scanner(System.in);
        scannerInput = scanner.nextLine();
        if (String.valueOf(scannerInput).equals("1")) {
            for (Book book : database.getDatabase()){
                if (book.isLoaned()){
                    System.out.println(" ");
                    System.out.println(book.getBookDetails());
                    System.out.println("Times book has been loaned: " + book.getTimesLoaned());
                }
            }
            System.out.println(" ");
            System.out.println("Press 2: You absolutely must make sure you have added new header and column to books_data.csv");
            System.out.println("This will help us understand how many times a book has been loaned...");
            scannerInput = scanner.nextLine();
            if(String.valueOf(scannerInput).equals("2")) {
                try {
                    createNewHeader();
                } catch (IOException | CsvException e){
                    System.out.println(e);
                }
                System.out.println("Press 3: Update logs and go for coffee break... you've earned it");
                scannerInput = scanner.nextLine();
                if (String.valueOf(scannerInput).equals("3")) {
                    for (Book book : database.getDatabase()) {
                        if (book.isLoaned()) {
                            try {
                                logLoanedBooks(String.valueOf(book.getStoreId()), book.getTitle(), book.getAuthor(), book.getGenre(), book.getSubGenre(), book.getPublisher(), book.getLoaner());
                                logTimesBookLoaned(String.valueOf(book.getStoreId()), book.getIndex());
                            } catch (IOException | CsvException e) {
                                System.out.println(" ");
                                System.out.println("The Logging function has not worked!");
                                Library();
                            }
                        }
                    }
                    startApplication();
                } else Library();
            } else Library();
        } else Library();
    }
    public void Exit() {
        System.exit(0);
    }
}
