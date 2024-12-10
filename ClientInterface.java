/**
 * Interface defining the structure of a Client in a Server/Client System.
 *
 * @author Yog Trivedi
 * @version November 17, 2024
 */
public interface ClientInterface {
    /**
     * Sends a request to the server and waits for a response.
     *
     * @param request The request to be sent as a string.
     * @return The response from the server as an Object.
     */
    Object sendRequest(String request);

    /**
     * Initializes and runs the client, setting up GUI components.
     */
    void run();
}
