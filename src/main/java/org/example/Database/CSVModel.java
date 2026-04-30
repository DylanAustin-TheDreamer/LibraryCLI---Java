package org.example.Database;

import com.opencsv.bean.CsvBindByName;

import java.util.Objects;

// CSV model will be the model to make individual objects, that will all go in a list in database class.
// that is my plan
public class CSVModel {
    @CsvBindByName(column = "Number", required = true)
    private Integer storeId;
    @CsvBindByName(column = "Title", required = true)
    private String title;
    @CsvBindByName(column = "Author", required = false)
    private String author;
    @CsvBindByName(column = "Genre", required = true)
    private String genre;
    @CsvBindByName(column = "SubGenre", required = true)
    private String subGenre;
    @CsvBindByName(column = "Publisher", required = true)
    private String publisher;

    public CSVModel() {
        // required by OpenCSV
    }

    public CSVModel(Integer storeId, String title, String author, String genre, String subGenre, String publisher){
        this.storeId = storeId;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.subGenre = subGenre;
        this.publisher = publisher;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getSubGenre() {
        return subGenre;
    }

    public String getGenre() {
        return genre;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public Integer getStoreId() {
        return storeId;
    }
}
