import java.io.IOException;

/**
 * Interface for the Client class
 *
 * @author Yog Trivedi
 * @version November 17, 2024
 */

public interface ClientInterface {

    Object sendRequest(Object request);
    void closeConnection();

}
