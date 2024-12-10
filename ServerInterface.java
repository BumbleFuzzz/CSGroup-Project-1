import java.io.IOException;

/**
 * Interface defining the structure of a Server in a Server/Client System.
 *
 * @author Yog Trivedi
 * @version November 17, 2024
 */
public interface ServerInterface {
    /**
     * Starts the server and listens for client connections.
     */
    void start();

    /**
     * Loads the user database from persistent storage.
     *
     * @throws IOException If an I/O error occurs during loading.
     */
    void loadUserDatabase() throws IOException;

    /**
     * Saves the current state of the user database to persistent storage.
     *
     * @throws IOException If an I/O error occurs during saving.
     */
    void saveUserDatabase() throws IOException;

    /**
     * Processes a client request and returns a response.
     *
     * @param request The client request as an object.
     * @return The response to the client.
     */
    Object processRequest(Object request);
}
