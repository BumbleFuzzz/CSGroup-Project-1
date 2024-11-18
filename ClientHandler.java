import java.io.*;
import java.net.*;

/**
 * Handles the running of a Client, allows for our Server to host multiple Clients
 *
 * @author Asher Earnhart
 * @version November 17, 2024
 */

public class ClientHandler implements Runnable, ClientHandlerInterface {
    private Socket clientSocket;
    private Server server;
    private ObjectOutputStream outStream;
    private ObjectInputStream inStream;

    public ClientHandler(Socket socket, Server server) {
        this.clientSocket = socket;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            outStream = new ObjectOutputStream(clientSocket.getOutputStream());
            inStream = new ObjectInputStream(clientSocket.getInputStream());

            Object request;
            while ((request = inStream.readObject()) != null) {
                server.handleRequest(request, outStream);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
