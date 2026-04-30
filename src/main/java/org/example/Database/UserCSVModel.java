package org.example.Database;
import com.opencsv.bean.CsvBindByName;

import java.util.Objects;

// CSV model will be the model to make individual objects, that will all go in a list in database class.
// that is my plan
public class UserCSVModel {
    @CsvBindByName(column = "User", required = true)
    private String userName;
    @CsvBindByName(column = "Password", required = true)
    private String password;
    @CsvBindByName(column = "Books", required = false)
    private String bookIndex;

    public UserCSVModel() {
        // required by OpenCSV
    }

    public UserCSVModel(String userName, String password, String bookIndex){
        this.userName = userName;
        this.password = password;
        this.bookIndex = bookIndex;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getBookIndex() {
        return bookIndex;
    }
}
