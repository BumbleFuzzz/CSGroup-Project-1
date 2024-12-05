import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;



/**
 * Handles the need to create files that represent users.
 *
 * @author Austin Napier
 * @version November 3, 2024
 */

public class UserDatabase implements UserDatabaseInterface{
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

 
public List<User> searchUsers(Predicate<User> filter) {
    return listOfUsers.stream()
                      .filter(filter)
                      .collect(Collectors.toList());
}

// Example usage:
// Searching for a user by username (assuming User class has a getUsername() method)
// List<User> result = userDatabase.searchUsers(user -> user.getUsername().equalsIgnoreCase("exampleUsername"));

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

    public String databaseFileIntoString() {
        File databaseFile = new File("UserDatabase.txt");
        String toReturn = "";
        
        try (BufferedReader br = new BufferedReader(new FileReader(databaseFile));) {
            String line = br.readLine();
            while (line != null) {
                toReturn += line + "\n";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return toReturn;
    }
}
