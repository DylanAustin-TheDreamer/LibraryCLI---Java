package org.example.Database;

import org.example.Books.Book;
import org.example.User.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Database {
    private List<Book> book = new ArrayList<>();
    private List<User> user = new ArrayList<>();

    public Database() {
    }

    public void addToDatabase(Book book){
        this.book.addLast(book);
    }
    public void addToUserDatabase(User user){
        this.user.addLast(user);
    }
    public Book getSpecific(String index){
        if (!Objects.equals(index, "") || !Objects.equals(index, " ") || !Objects.equals(index, null)) {
            try {
                int bookIndex = Integer.parseInt(index);
                for (Book book : this.book) {
                    if (bookIndex == book.getIndex()) {
                        return book;
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("No results found matching your terms");
                return null;
            }
        }
        return null;
    }
    public void setDatabaseBookToLoan(int index, String name){
        for (Book book : this.book) {
            if (index == book.getIndex()) {
                book.setLoaned(name);
                System.out.println(book.getBookDetails());
            }
        }
    }
    public Book searchTitle(String title){
        for (Book book : this.book) {
            if (book.searchTitle(title)) {
                return book;
            }
        }
        System.out.println("No results found matching your terms");
        return null;
    }

    // Universal: return database as a whole
    public void displayAllBooks(){
        for (Book book : this.book){
            System.out.println(book.getBookDetails());
        }
    }
    // Pass the database list for use in other classes like application
    public List<Book> getDatabase() {
        return this.book;
    }
    public List<User> getUserDatabase() {
        return this.user;
    }
}
