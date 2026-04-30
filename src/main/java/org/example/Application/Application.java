package org.example.Application;

import com.opencsv.exceptions.CsvException;
import org.example.Books.Book;
import org.example.Database.Database;
import org.example.User.Admin;
import org.example.User.User;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import static org.example.Database.DatabaseUtilities.saveUsers;
import static org.example.Database.DatabaseUtilities.updateExistingUsers;

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
                this.user = new User(users.getName(), users.getPassword(), null, false);
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
                System.out.println("Type the title and press enter...");
                Scanner scannerTitle = new Scanner(System.in);
                scannerInput = scannerTitle.nextLine();
                String title = String.valueOf(scannerInput);
                Book returnedBook = this.database.searchTitle(title);
                System.out.println(returnedBook.getBookDetails());
                System.out.println(" ");

                // This section below is not working - and it is the simplest code I have
                if(!returnedBook.isLoaned()) {
                    if (user != null) {
                        System.out.println("Press 1: Loan");
                    } else System.out.println("If you wish to loan, you must log in");
                } else System.out.println("This book is already out on loan");
                // --------------------------------

                System.out.println("Press 2: Back to library");
                scannerInput = scannerTitle.nextLine();
                if (String.valueOf(scannerInput).equals("1")) {
                    if (user != null) {
                        System.out.println(" ");
                        this.database.getSpecific(Integer.parseInt(String.valueOf(returnedBook.getIndex())), user.getName());
                    } else this.Library();
                } else {
                    this.Library();
                }
                break;
            case "2":
                // code block
                System.out.println("---");
                System.out.println("Index from 1 to " + books.getLast().getIndex());
                System.out.println("Type the book ID index and press enter...");
                Scanner scannerIndex = new Scanner(System.in);
                scannerInput = scannerIndex.nextLine();
                String convert = String.valueOf(scannerInput);
                this.database.getSpecific(Integer.parseInt(convert), null);
                System.out.println(" ");
                if (user != null) {
                    System.out.println("Press 1: Loan");
                } else System.out.println("If you wish to loan, you must log in");
                System.out.println("Press 2: Back to library");
                scannerInput = scannerIndex.nextLine();
                if (String.valueOf(scannerInput).equals("1")) {
                    if (user != null) {
                        System.out.println(" ");
                        this.database.getSpecific(Integer.parseInt(convert), user.getName());
                        try {
                            updateExistingUsers(user.getName(), Integer.parseInt(convert));
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
            default:
                // code block
                System.out.println("---");
                System.out.println("Refer to options and try again...");
                this.startApplication();
        }
    }
    public void Exit() {
        System.exit(0);
    }
}
