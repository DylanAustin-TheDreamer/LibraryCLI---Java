package org.example.Database;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvException;
import org.example.Books.Book;
import org.example.User.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DatabaseUtilities {
    public DatabaseUtilities(){
    }
    public static Database databaseUtility(Database userDatabase) throws FileNotFoundException {
        System.out.println("DATABASE UTILITY CALLED");
        FileReader filereader = new FileReader("src/main/java/org/example/Database/books_data.csv");
        CSVReader csvReader = new CSVReader(filereader);
        Database database = new Database();

        ArrayList<CSVModel> dataObjects = new ArrayList<>(
                new CsvToBeanBuilder<CSVModel>(csvReader)
                        .withType(CSVModel.class)
                        .withIgnoreLeadingWhiteSpace(true)
                        .withIgnoreQuotations(true)
                        .build()
                        .stream()
                        .toList()
        );

        for (CSVModel model : dataObjects) {
            Book book = new Book(
                    model.getStoreId(),
                    model.getTitle(),
                    model.getAuthor(),
                    model.getGenre(),
                    model.getSubGenre(),
                    model.getPublisher(),
                    false,
                    null
            );
            database.addToDatabase(book);
        }
        System.out.println("USERS SIZE: " + userDatabase.getUserDatabase().size());
        List<User> users = userDatabase.getUserDatabase();
        for (User user : users) {
            String bookIndex = user.getBookIndex();

            if (bookIndex != null && !bookIndex.equals("null")) {
                for (Book book : database.getDatabase()) {
                    if (bookIndex.equals(String.valueOf(book.getIndex()))) {
                        book.setLoaned(user.getName());
                        System.out.println("SETTING LOAN: " + book.getIndex() + " -> " + user.getName());
                    }
                }
            }
        }

        return database;
    }
    public static void saveUsers(String filePath, String user, String password, String books ) throws IOException {
        File file = new File(filePath);
        try {
            FileReader fileReader = new FileReader(file);
            FileWriter fw = new FileWriter(file, true);

            // create CSVWriter object filewriter object as parameter
            CSVWriter writer = new CSVWriter(fw);
            // add data to csv
            String[] data1 = {user, password, "null"};
            // ignore the yellow squigglies - doesn't know what it's talking about. Tried its solution and it doesn't work
            writer.writeNext(data1);
            // closing writer connection
            writer.close();
            fw.close();
        }
        catch (IOException e) {
            // create FileWriter object with file as parameter
            FileWriter outputfile = new FileWriter(file);

            // create CSVWriter object filewriter object as parameter
            CSVWriter writer = new CSVWriter(outputfile);

            // adding header to csv
            String[] header = {"User", "Password", "Books"};
            writer.writeNext(header);

            // add data to csv
            String[] data1 = {user, password, "null"};
            writer.writeNext(data1);
            // closing writer connection
            writer.close();
        }
    }
    public static Database loadUsers() throws FileNotFoundException {
        FileReader filereader = new FileReader("src/main/java/org/example/User/users_data.csv");
        CSVReader csvReader = new CSVReader(filereader);
        Database userDatabase = new Database();

        ArrayList<UserCSVModel> dataObjects = new ArrayList<>(new CsvToBeanBuilder<UserCSVModel>(csvReader).withType(UserCSVModel.class).withIgnoreLeadingWhiteSpace(true).withIgnoreQuotations(true).build().stream().toList());
        for (UserCSVModel model : dataObjects) {
            User users = new User(model.getUserName(), model.getPassword(), model.getBookIndex(), false);
            userDatabase.addToUserDatabase(users);
        }
        return userDatabase;
    }

    public static void updateExistingUsers(String name, int bookIndex) throws IOException, CsvException, FileNotFoundException {
        File inputFile = new File("src/main/java/org/example/User/users_data.csv");

        // Read existing file
        CSVReader reader = new CSVReader(new FileReader(inputFile));
        List<String[]> csvBody = reader.readAll();
        // get CSV row column  and replace with by using row and column
        csvBody.get(returnIndexCSVBody(csvBody, name))[2] = String.valueOf(bookIndex);
        reader.close();

        // Write to CSV file which is open
        CSVWriter writer = new CSVWriter(new FileWriter(inputFile));
        writer.writeAll(csvBody);
        writer.flush();
        writer.close();
    }
    //Number,Title,Author,Genre,SubGenre,Publisher
    public static void logLoanedBooks(String number, String title, String author, String genre, String subGenre, String publisher, String loaner) throws IOException {
        File file = new File("src/main/java/org/example/User/admin_logs_data.csv");
        try {
            FileReader fileReader = new FileReader(file);
            FileWriter fw = new FileWriter(file, true);

            // create CSVWriter object filewriter object as parameter
            CSVWriter writer = new CSVWriter(fw);
            // add data to csv
            String[] data1 = {number, title, author, genre, subGenre, publisher, loaner};
            // ignore the yellow squigglies - doesn't know what it's talking about. Tried its solution and it doesn't work
            writer.writeNext(data1);
            // closing writer connection
            writer.close();
            fw.close();
        }
        catch (IOException e) {
            // create FileWriter object with file as parameter
            FileWriter outputfile = new FileWriter(file);

            // create CSVWriter object filewriter object as parameter
            CSVWriter writer = new CSVWriter(outputfile);

            // adding header to csv
            String[] header = {"Number", "Title", "Author", "Genre", "SubGenre", "Publisher", "Loaner"};
            writer.writeNext(header);

            // add data to csv
            String[] data1 = {number, title, author, genre, subGenre, publisher, loaner};
            writer.writeNext(data1);
            // closing writer connection
            writer.close();
        }
    }
    public static int returnIndexCSVBody(List<String[]> csvBody, String name){
        for (int i = 1; i < csvBody.size(); i++){
            String[] row = csvBody.get(i);
            if (name.equals(row[0].trim())){
                return i;
            }
        }
        return -1;
    }
}
