import java.io.IOException;

public interface ClientHandlerInterface extends Runnable {


    void initializeStreams() throws IOException;
    
    void handleClientRequests() throws IOException, ClassNotFoundException;

    void closeConnection() throws IOException;

}