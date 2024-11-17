import java.io.*;
import java.net.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Server implements ServerInterface, Runnable {
    private ConcurrentHashMap<String, User> userDatabase;  // Thread-safe storage
    private List<User> activeUsers;
    private boolean isRunning = true;

    public Server() {
        userDatabase = new ConcurrentHashMap<>();
        activeUsers = new CopyOnWriteArrayList<>();
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
    public void addUser(User user) {
        // Add user to the activeUsers list
        activeUsers.add(user);
    }

    @Override
    public void removeUser(User user) {
        // Remove user from the activeUsers list
        activeUsers.remove(user);
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
