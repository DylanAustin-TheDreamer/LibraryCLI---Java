 import org.example.Application.Application;
 import org.example.Database.Database;
 import org.example.Database.DatabaseUtilities;

 import java.io.FileNotFoundException;

 public static void main(String[] args) throws FileNotFoundException {
    Database userDatabase = DatabaseUtilities.loadUsers();
    Database database = DatabaseUtilities.databaseUtility(userDatabase);
    Application app = new Application(database, userDatabase);
    app.startApplication();
 }