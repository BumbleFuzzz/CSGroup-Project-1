import java.io.*;
import java.util.ArrayList;

/**
 * Handles the need to create files that represent users.
 *
 * @author Austin Napier
 * @version November 3, 2024
 */

public class UserDatabase {
    private ArrayList<User> listOfUsers ;
    private File currentDBFile;

    public UserDatabase() { // Initializes a UserDatabase object
        listOfUsers = new ArrayList<User>();
    }

    public void addUser(User user) { // Adds user to the database
        listOfUsers.add(user);
    }

    public void removeUser(User user) {
        listOfUsers.remove(user);
    }

    public boolean containsUser(User user) {
        return listOfUsers.contains(user);
    }

    public ArrayList<User> getUsers() {
        return listOfUsers;
    }

    public File getCurrentDBFile() {
        return currentDBFile;
    }

    public void createDatabaseFile() { // Creates a file containing all the info of every user in this Database's list, which can then be fetched with getCurrentDBFile
        File databaseFile = new File("UserDatabase.txt");

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(databaseFile));) {
            for (User user : listOfUsers) {
                bw.write(user.toString());
                bw.write("\n");
            }
            currentDBFile = databaseFile;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
