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
            while ((request = readRequest()) != null) {
                server.handleRequest(request, outStream);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                closeConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Object readRequest() throws IOException, ClassNotFoundException {
        if (inStream == null) {
            throw new IOException("Input stream is not initialized.");
        }
        return inStream.readObject();
    }

    @Override
    public void sendResponse(Object response) throws IOException {
        if (outStream == null) {
            throw new IOException("Output stream is not initialized.");
        }
        outStream.writeObject(response);
        outStream.flush(); // Ensure the response is sent immediately
    }

    @Override
    public void closeConnection() throws IOException {
        if (clientSocket != null && !clientSocket.isClosed()) {
            clientSocket.close();
        }
        if (inStream != null) {
            inStream.close();
        }
        if (outStream != null) {
            outStream.close();
        }
    }
}
