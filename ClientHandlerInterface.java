import java.io.IOException;

public interface ClientHandlerInterface extends Runnable {
    
    void run();

    void sendResponse(Object response) throws IOException;

    Object readRequest() throws IOException, ClassNotFoundException;

    void closeConnection() throws IOException;
}