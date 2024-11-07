import java.io.*;
import java.net.*;
import java.util.concurrent.ConcurrentHashMap;

public class Server implements ServerInterface, Runnable {
    private ConcurrentHashMap<String, User> userDatabase;  // Thread-safe storage
    private boolean isRunning = true;

    public Server() {
        userDatabase = new ConcurrentHashMap<>();
    }

    public static void main(String[] args) {
        Server server = new Server();
        new Thread(server).start(); // Starts the server on a separate thread
    }

    @Override
    public void startServer(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started on port " + port);

            while (isRunning) {
                Socket clientSocket = serverSocket.accept();
                new Thread(new ClientHandler(clientSocket, this)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stopServer() {
        isRunning = false;
    }

    @Override
    public void handleRequest(Object request, ObjectOutputStream outStream) {
        // Handle client request (e.g., fetching or updating user data)
    }

    @Override
    public boolean addUser(User user) {
        return false;
    }

    @Override
    public boolean removeUser(User user) {
        return false;
    }

    @Override
    public User getUser(String username) {
        return null;
    }

    @Override
    public void run() {
        startServer(12345);
    }

    // Additional methods to manipulate the userDatabase
}
