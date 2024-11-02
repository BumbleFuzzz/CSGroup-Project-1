import java.io.*;
import java.util.ArrayList;
public class UserDatabase {
    private ArrayList<User> listOfUsers ;
    private File currentDBFile;

    public UserDatabase() {
        listOfUsers = new ArrayList<User>();
    }

    public void addUser(User user) {
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

    public void createDatabaseFile() {
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
