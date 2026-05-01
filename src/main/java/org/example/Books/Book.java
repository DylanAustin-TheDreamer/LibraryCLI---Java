package org.example.Books;

import java.util.Arrays;
import java.util.Objects;

public class Book {
    //Number,Title,Author,Genre,SubGenre,Publisher
    private Integer storeId;
    private String title;
    private String author;
    private String genre;
    private String subGenre;
    private String publisher;
    private boolean isLoaned;
    private String loaner;

    public Book(Integer storeId, String title, String author, String genre, String subGenre, String publisher, boolean isLoaned, String loaner) {
        this.storeId = storeId;
        this.title = title;
        if(Objects.equals(author, "")){
            this.author = "Unknown Author";
        } else this.author = author;
        this.genre = genre;
        this.subGenre = subGenre;
        this.publisher = publisher;
        this.isLoaned = isLoaned;
        this.loaner = loaner;
    }
    // Let's make some search check methods
    public boolean searchTitle(String title){
        if (title.toLowerCase().equals(this.title.toLowerCase())){
            return true;
        } else return false;
    }
    //use getters and setters to edit loan details - going to make this admin access only
    public void setLoaned(String user) {
        this.loaner = user;
        this.isLoaned = true;
    }
    public void returnLoan() {
        this.loaner = null;
        this.isLoaned = false;
    }
    public boolean isLoaned() {
        return this.isLoaned;
    }

    public int getIndex(){
        return this.storeId;
    }

    // Just want a big bulk of details to keep this file small
    public String getBookDetails() {
        //private Map<String, Book> booksByTitle = new HashMap<>();
        String[] bookDetails = new String[] {
                "Title: " + this.title,
                "Author: " + this.author.replace(",", ""),
                "Genre: " + this.genre,
                "SubGenre: " + this.subGenre,
                "Publisher: " + this.publisher
        };
        // is loaned is true here but isLoaned() doesn't return true if loaned... argh...
        if (this.isLoaned) {
            return "Book ID: " + this.storeId + "\n" + "Loaned to: " + this.loaner + "\n" + Arrays.toString(bookDetails);
        } else return "Book ID: " + this.storeId + "\n" + Arrays.toString(bookDetails);
    }

    public Integer getStoreId() {
        return storeId;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public String getSubGenre() {
        return subGenre;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getLoaner() {
        return loaner;
    }
}
