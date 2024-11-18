import java.io.*;
import java.net.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Handles the Server side of the Server/Client System
 *
 * @author Asher Earnhart
 * @version November 17, 2024
 */

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
    public boolean addUser(User user) { // Handles the addition of a new user to the database
        if (user == null) {
            return false;
        } else {
            userDatabase.put(user.getUsername(), user);
            return true;
        }
    }

    @Override
    public boolean removeUser(User user) { // Handles removal of a user from the database
        if (userDatabase.containsKey(user.getUsername()) && userDatabase.contains(user)) {
            userDatabase.remove(user);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public User getUser(String username) {
        return userDatabase.get(username);
    }

    @Override
    public void run() {
        startServer(12345);
    }

    // Additional methods to manipulate the userDatabase
}
