import java.io.ObjectOutputStream;

/**
 * Interface for the Server class
 *
 * @author Yog Trivedi
 * @version November 17, 2024
 */

public interface ServerInterface {
    void startServer(int port);
    void stopServer();
    void handleRequest(Object request, ObjectOutputStream outStream);
    void addUser(String username, String password, String bio);    // Example method for user management
    void removeUser(User user);
    User getUser(String username);
}
