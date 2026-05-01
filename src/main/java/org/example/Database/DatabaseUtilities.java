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
                    null,
                    model.getTimesLoaned()
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
    public static void createNewHeader() throws IOException, CsvException{
        File inputFile = new File("src/main/java/org/example/Database/books_data.csv");
        // Read existing file
        CSVReader reader = new CSVReader(new FileReader(inputFile));
        List<String[]> csvBody = reader.readAll();
        // get CSV row column  and replace with by using row and column
        try {
            String[] oldHeader = csvBody.get(0);

            // HAD SOME HELP FROM AI - OTHERWISE I WOULD HAVE HAD TO UPDATE BOOKS_DATA MANUALLY
            // THIS WASN'T ALLOWED IN THE PROJECT BREIF
            // I'M SORRY - Just learning Java

            // create new header with +1 column
            String[] newHeader = new String[oldHeader.length + 1];
            if (newHeader.length > 6) {

                // copy old values
                System.arraycopy(oldHeader, 0, newHeader, 0, oldHeader.length);

                // add new column name at the end
                newHeader[oldHeader.length] = "Times Loaned"; // <-- change this

                // replace header row
                csvBody.set(0, newHeader);
                for (int i = 1; i < csvBody.size(); i++) {
                    String[] row = csvBody.get(i);

                    String[] newRow = new String[row.length + 1];
                    System.arraycopy(row, 0, newRow, 0, row.length);

                    newRow[row.length] = ""; // or some value

                    csvBody.set(i, newRow);
                }
                // END OF AI EDIT

                reader.close();

                // Write to CSV file which is open
                CSVWriter writer = new CSVWriter(new FileWriter(inputFile));
                writer.writeAll(csvBody);
                writer.flush();
                writer.close();
            } else return;
        } catch (Exception e){
            System.out.println("Error adding header and col to books_data.csv");
        }
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

    public static void updateExistingUsers(String name, int bookIndex) throws IOException, CsvException {
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
    public static void logTimesBookLoaned(String name, int bookIndex) throws IOException, CsvException {
        int counter = 0;
        File inputFile = new File("src/main/java/org/example/Database/books_data.csv");

        // Read existing file
        CSVReader reader = new CSVReader(new FileReader(inputFile));
        List<String[]> csvBody = reader.readAll();
        // get CSV row column  and replace with by using row and column
        try {

            int timesLoaned = Integer.parseInt(csvBody.get(returnIndexCSVBody(csvBody, String.valueOf(bookIndex)))[6]);
            csvBody.get(returnIndexCSVBody(csvBody, String.valueOf(bookIndex)))[6] = String.valueOf(timesLoaned + 1);
            reader.close();

            // Write to CSV file which is open
            CSVWriter writer = new CSVWriter(new FileWriter(inputFile));
            writer.writeAll(csvBody);
            writer.flush();
            writer.close();
            //
            timesLoaned ++;
            System.out.println("Book Index: " + name + ": " + "This book has been loaned " + timesLoaned + "times" );
        } catch (Exception e){
            // add timesLoaned data for first time
            System.out.println("Book Index: " + name + ": " + "This book has been loaned for the first time");
            // Write to CSV file which is open
            CSVWriter writer = new CSVWriter(new FileWriter(inputFile));
            csvBody.get(returnIndexCSVBody(csvBody, String.valueOf(bookIndex)))[6] = String.valueOf(counter + 1);
            reader.close();
            writer.writeAll(csvBody);
            writer.flush();
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
