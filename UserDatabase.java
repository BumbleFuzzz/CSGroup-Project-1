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
    private static Object lock = new Object();

    public UserDatabase() { // Initializes a UserDatabase object
        listOfUsers = new ArrayList<User>();
        currentDBFile = new File("UserDatabase.txt");
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

 
    public User searchUser(String pUsername) {
        synchronized (lock) {
            createDatabaseFile();
            File databaseFile = this.currentDBFile;
            User resultSerachUser = null;
            try (BufferedReader br = new BufferedReader(new FileReader(this.currentDBFile));) {
                String line = br.readLine();
                while (line != null) {
                    if(line.indexOf(pUsername) == 0) {
                        String[] parts = line.split(",");
                        String username = parts[0];
                        String password = parts[1];
                        String biography = parts[2];

                        List<String> friends = new ArrayList<>();

                        for (int i = 4; i < parts.length; i += 3) {
                            if (parts[i].startsWith("&")) {
                                String friendName = parts[i].substring(1); // Remove the '&'
                                friends.add(friendName);
                            }
                        }

                        resultSerachUser =  new User(username, password, biography);
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return resultSerachUser;
        }
    }

    public ArrayList<User> getUsers() {
        return listOfUsers;
    }

    public File getCurrentDBFile() {
        return currentDBFile;
    }

    public void createDatabaseFile() { // Creates a file containing all the info of every user in this Database's list, which can then be fetched with getCurrentDBFile
        synchronized (lock) {
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

    public String databaseFileIntoString() {
        synchronized (lock) {
            File databaseFile = new File("UserDatabase.txt");
            String toReturn = "";

            try (BufferedReader br = new BufferedReader(new FileReader(databaseFile));) {
                String line = br.readLine();
                while (line != null) {
                    toReturn += line + "\n";
                    line = br.readLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return toReturn;
        }
    }
}
