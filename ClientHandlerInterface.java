import java.io.IOException;

/**
 * Interface for the ClientHandler class
 *
 * @author Yog Trivedi
 * @version November 17, 2024
 */

public interface ClientHandlerInterface extends Runnable {


    void initializeStreams() throws IOException;
    
    void handleClientRequests() throws IOException, ClassNotFoundException;

    void closeConnection() throws IOException;

}
