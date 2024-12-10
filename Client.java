import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

/**
 * Handles all interactions on the Client side of the Server/Client System
 *
 * @author Asher Earnhart
 * @version November 17, 2024
 */
public class Client implements ClientInterface, Runnable {
    private JFrame loginOrSignupFrame;
    private JFrame loginFrame;
    private JFrame signupFrame;
    private JFrame mainMenuFrame;
    private JFrame profileFrame;
    private JButton loginButton;
    private JButton signUpButton;
    private JButton loginAttemptButton;
    private JButton accountCreationButton;
    private JButton editBioButton;
    private JTextField usernameInput;
    private JTextField passwordInput;
    private JTextField usernameSignupInput;
    private JTextField passwordSignupInput;
    private JTextArea profileName;
    private JTextArea profileBio;
    private User loggedInUser;
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

    @Override
    public void run() {
        loginOrSignupFrame = new JFrame("User Login/Signup");
        loginFrame = new JFrame("User Login");
        signupFrame = new JFrame("User Signup");
        mainMenuFrame = new JFrame("Main Menu");
        profileFrame = new JFrame("Profile");

        setupLoginOrSignupFrame();
        setupLoginFrame();
        setupSignupFrame();
        setupProfileFrame();
    }

    private void setupLoginOrSignupFrame() {
        loginButton = new JButton("Login");
        loginButton.addActionListener(e -> {
            loginOrSignupFrame.setVisible(false);
            loginFrame.setVisible(true);
        });

        signUpButton = new JButton("Sign Up");
        signUpButton.addActionListener(e -> {
            loginOrSignupFrame.setVisible(false);
            signupFrame.setVisible(true);
        });

        JLabel welcomeMessage = new JLabel("Welcome to FriendFusion!");
        welcomeMessage.setFont(new Font("Serif", Font.BOLD, 25));

        loginOrSignupFrame.setLayout(new BorderLayout());
        loginOrSignupFrame.add(welcomeMessage, BorderLayout.NORTH);
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(loginButton);
        buttonPanel.add(signUpButton);
        loginOrSignupFrame.add(buttonPanel, BorderLayout.CENTER);
        loginOrSignupFrame.setSize(600, 150);
        loginOrSignupFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginOrSignupFrame.setVisible(true);
    }

    private void setupLoginFrame() {
        JLabel usernameLabel = new JLabel("Username: ");
        JLabel passwordLabel = new JLabel("Password: ");
        usernameInput = new JTextField(10);
        passwordInput = new JTextField(10);
        loginAttemptButton = new JButton("Login");

        loginAttemptButton.addActionListener(e -> {
            String username = usernameInput.getText();
            String password = passwordInput.getText();
            String response = (String) sendRequest("LOGIN," + username + "," + password);

            if (response != null && response.startsWith("LOGIN_SUCCESS")) {
                loggedInUser = new User(username, password, response.split(",")[1]); // Bio
                JOptionPane.showMessageDialog(null, "Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                loginFrame.setVisible(false);
                setupMainMenuFrame();
            } else {
                JOptionPane.showMessageDialog(null, "Login failed. Check username and password.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JPanel loginPanel = new JPanel(new GridLayout(3, 2));
        loginPanel.add(usernameLabel);
        loginPanel.add(usernameInput);
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordInput);
        loginPanel.add(loginAttemptButton);

        loginFrame.add(loginPanel);
        loginFrame.setSize(400, 200);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void setupSignupFrame() {
        JLabel usernameLabel = new JLabel("Username: ");
        JLabel passwordLabel = new JLabel("Password: ");
        usernameSignupInput = new JTextField(10);
        passwordSignupInput = new JTextField(10);
        accountCreationButton = new JButton("Sign Up");

        accountCreationButton.addActionListener(e -> {
            String username = usernameSignupInput.getText();
            String password = passwordSignupInput.getText();
            String bio = "Empty Bio";
            String response = (String) sendRequest("SIGNUP," + username + "," + password + "," + bio);

            if ("SIGNUP_SUCCESS".equals(response)) {
                JOptionPane.showMessageDialog(null, "Account created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                signupFrame.setVisible(false);
                loginOrSignupFrame.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Signup failed. Username might already exist.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JPanel signupPanel = new JPanel(new GridLayout(3, 2));
        signupPanel.add(usernameLabel);
        signupPanel.add(usernameSignupInput);
        signupPanel.add(passwordLabel);
        signupPanel.add(passwordSignupInput);
        signupPanel.add(accountCreationButton);

        signupFrame.add(signupPanel);
        signupFrame.setSize(400, 200);
        signupFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void setupMainMenuFrame() {
        mainMenuFrame.setLayout(new BorderLayout());
        JButton profileButton = new JButton("Profile");
        profileButton.addActionListener(e -> {
            profileName.setText("Username: " + loggedInUser.getUsername());
            profileBio.setText("Biography: " + loggedInUser.getBiography());
            mainMenuFrame.setVisible(false);
            profileFrame.setVisible(true);
        });

        mainMenuFrame.add(profileButton, BorderLayout.CENTER);
        mainMenuFrame.setSize(400, 200);
        mainMenuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainMenuFrame.setVisible(true);
    }

    private void setupProfileFrame() {
        profileName = new JTextArea();
        profileBio = new JTextArea();
        profileBio.setEditable(false);

        editBioButton = new JButton("Edit Bio");
        editBioButton.addActionListener(e -> {
            String newBio = JOptionPane.showInputDialog("Enter new bio:");
            if (newBio != null && !newBio.isEmpty()) {
                String response = (String) sendRequest("UPDATE_BIO," + loggedInUser.getUsername() + "," + newBio);
                if ("UPDATE_BIO_SUCCESS".equals(response)) {
                    loggedInUser.setBiography(newBio);
                    profileBio.setText("Biography: " + newBio);
                    JOptionPane.showMessageDialog(null, "Bio updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to update bio.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JPanel profilePanel = new JPanel(new BorderLayout());
        profilePanel.add(profileName, BorderLayout.NORTH);
        profilePanel.add(profileBio, BorderLayout.CENTER);
        profilePanel.add(editBioButton, BorderLayout.SOUTH);

        profileFrame.add(profilePanel);
        profileFrame.setSize(400, 300);
        profileFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public Object sendRequest(String request) {
        try {
            outStream.writeObject(request);
            return inStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Client("localhost", 12345));
    }
}