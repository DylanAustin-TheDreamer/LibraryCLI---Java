# Library CLI - Java
- By Dylan Austin

---
## Purpose of this project

- The purpose of this project, is to apply what I have learned in java to make a library application in the terminal.
- I have been using .csv files as a database.

## Technology used

- IntelliJ IDEA IDE
- Maven
- OpenCSV

---
# How to test

- Clone this repo into your IntelliJ IDE
- Go to file path:  src/main/java/org/example/Main.java
- And run Main.java

# IMPORTANT: The ADMIN mini game:

- To make admin use code: 6767
- Go to the library and then  admin tools
- When you are told to press 2, the books_data.csv is going to update with a new header and columns for every single book in the database.
- Please check database before and after in order to test I have not manually edited books_data.csv by hand.
- If you do this correctly, books_data.csv will have a header "Times Loaned" for every book. This is how I am keeping consistency for my book data.
- Admin logs and User data is seperate from books_data.csv. Any other method to handle times books were loaned, would have required extra complexity and handling. I tried this in admin logs instead and it was a terror.

- HAVE FUN!

---
## Notes

- There has been no unit testing in this application since there were a lot of things to change and refractor, whilst learning as I went a long. I also realized the deadline of the project would have been surpassed if I was to have integrated unit testing.
- Using good file structure and seperating my functionality into classes, I could develop without unit testing quite easily.

### Resources Used:

- A collection of videos: https://youtube.com/playlist?list=PLnZ8gMgzcvtgKV70Y2fpv854RJI4jL1fF&si=ru1eC8iMu8lWHbS2
- Writing to CSV: https://www.geeksforgeeks.org/java/writing-a-csv-file-in-java-using-opencsv/
- Updating existing CSV: https://stackoverflow.com/questions/4397907/updating-specific-cell-csv-file-using-java
- Link to openCSV dependencies: https://mvnrepository.com/artifact/com.opencsv/opencsv