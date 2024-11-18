import java.io.IOException;

/**
 * Interface for the ClientHandler class
 *
 * @author Yog Trivedi
 * @version November 17, 2024
 */

public interface ClientHandlerInterface extends Runnable {
    
    void run();

    void sendResponse(Object response) throws IOException;

    Object readRequest() throws IOException, ClassNotFoundException;

    void closeConnection() throws IOException;
}
