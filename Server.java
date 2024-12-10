import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Handles all interactions on the Server side of the Server/Client System
 *
 * @author Yog Trivedi
 * @version November 17, 2024
 */
public class Server implements ServerInterface {
    private ServerSocket serverSocket;
    private Map<String, User> userDatabase; // Simulating a database with a map

    public Server(int port) {
        try {
            serverSocket = new ServerSocket(port);
            userDatabase = new HashMap<>();
            loadUserDatabase(); // Load user data from file or initialize
            System.out.println("Server is running on port " + port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                new Thread(new ClientHandler(clientSocket)).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void loadUserDatabase() {
        // Load users from a file or initialize with default users
        File userFile = new File("userDatabase.txt");
        if (userFile.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(userFile))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(",");
                    userDatabase.put(parts[0], new User(parts[0], parts[1], parts[2])); // username, password, bio
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void saveUserDatabase() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("userDatabase.txt"))) {
            for (User user : userDatabase.values()) {
                bw.write(user.getUsername() + "," + user.getPassword() + "," + user.getBiography() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object processRequest(Object request) {
        return null;
    }

    private class ClientHandler implements Runnable {
        private Socket clientSocket;
        private ObjectOutputStream outStream;
        private ObjectInputStream inStream;

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            try {
                outStream = new ObjectOutputStream(clientSocket.getOutputStream());
                inStream = new ObjectInputStream(clientSocket.getInputStream());

                Object request;
                while ((request = inStream.readObject()) != null) {
                    Object response = processRequest(request);
                    outStream.writeObject(response);
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

        private Object processRequest(Object request) {
            if (request instanceof String) {
                String[] parts = ((String) request).split(",");
                String command = parts[0];
                switch (command) {
                    case "LOGIN":
                        return handleLogin(parts[1], parts[2]); // username, password
                    case "SIGNUP":
                        return handleSignup(parts[1], parts[2], parts[3]); // username, password, bio
                    case "UPDATE_BIO":
                        return handleUpdateBio(parts[1], parts[2]); // username, bio
                    default:
                        return "Unknown command";
                }
            }
            return "Invalid request";
        }

        private String handleLogin(String username, String password) {
            User user = userDatabase.get(username);
            if (user != null && user.getPassword().equals(password)) {
                return "LOGIN_SUCCESS," + user.getBiography();
            }
            return "LOGIN_FAIL";
        }

        private String handleSignup(String username, String password, String bio) {
            if (userDatabase.containsKey(username)) {
                return "SIGNUP_FAIL";
            }
            userDatabase.put(username, new User(username, password, bio));
            saveUserDatabase();
            return "SIGNUP_SUCCESS";
        }

        private String handleUpdateBio(String username, String bio) {
            User user = userDatabase.get(username);
            if (user != null) {
                user.setBiography(bio);
                saveUserDatabase();
                return "UPDATE_BIO_SUCCESS";
            }
            return "UPDATE_BIO_FAIL";
        }
    }

    public static void main(String[] args) {
        Server server = new Server(12345); // Port 12345
        server.start();
    }
}
