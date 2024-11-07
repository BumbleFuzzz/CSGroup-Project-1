import java.io.ObjectOutputStream;

public interface ServerInterface {
    void startServer(int port);
    void stopServer();
    void handleRequest(Object request, ObjectOutputStream outStream);
    boolean addUser(User user);    // Example method for user management
    boolean removeUser(User user);
    User getUser(String username);
}
