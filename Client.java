import java.io.*;
import java.net.*;

/**
 * Handles all interactions on the Client side of the Server/Client System
 *
 * @author Asher Earnhart
 * @version November 17, 2024
 */

public class Client implements ClientInterface {
    private Socket socket;
    private ObjectOutputStream outStream;
    private ObjectInputStream inStream;

    public Client(String serverAddress, int port) {
        try {
            socket = new Socket(serverAddress, port);
            outStream = new ObjectOutputStream(socket.getOutputStream());
            inStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Client client = new Client("localhost", 12345); // "localhost" refers to the server running on the same machine

        // Example of sending a request
        Object response = client.sendRequest("Sample request");
        System.out.println("Response from server: " + response);

        client.closeConnection(); // Closes the client connection
    }


    public Object sendRequest(Object request) {
        try {
            outStream.writeObject(request);
            return inStream.readObject(); // Read the server's response
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void closeConnection() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
