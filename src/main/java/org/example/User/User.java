package org.example.User;

public class User {
    private String name;
    private String password;
    private String bookIndex;
    public boolean admin = false;

    public User(String name, String password, String bookIndex, boolean admin){
        this.name = name;
        this.password = password;
        this.bookIndex = bookIndex;
        this.admin = admin;
    }

    public String getName() {
        return this.name;
    }
    public String getPassword() {
        return this.password;
    }
    public String getBookIndex() {
        return this.bookIndex;
    }
    public void setBookIndex(String bookIndex) {
        this.bookIndex = bookIndex;
    }
}
