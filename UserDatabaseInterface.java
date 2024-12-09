import java.io.File;
import java.util.ArrayList;

/**
 * Interface corresponding to the UserDatabase class
 *
 * @author Austin Napier
 * @version November 3, 2024
 */

public interface UserDatabaseInterface {
    public void addUser(User user);
    public void removeUser(User user);
    public boolean containsUser(User user);
    public ArrayList<User> getUsers();
    public File getCurrentDBFile();
    public void createDatabaseFile();
    public void updateDatabaseFile();

    public static User searchUser(String username) {
        return null;
    }
}
