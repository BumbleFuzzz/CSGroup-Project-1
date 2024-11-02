import java.io.File;
import java.util.ArrayList;

public interface UserDatabaseInterface {
    public void addUser(User user);
    public void removeUser(User user);
    public boolean containsUser(User user);
    public ArrayList<User> getUsers();
    public File getCurrentDBFile();
    public void createDatabaseFile();
}
