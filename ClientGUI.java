import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * GUI for the Client of the Social Media Program
 *
 * @author Austin Napier
 * @version 11/21/2024
 */

public class ClientGUI implements Runnable {
    JFrame loginOrSignupFrame;
    JFrame loginFrame;
    JFrame signupFrame;
    JFrame mainMenuFrame;
    JButton loginButton;
    JButton signUpButton;
    JButton loginAttemptButton;
    JButton accountCreationButton;
    JButton userSearchButton;
    JTextField usernameInput;
    JTextField passwordInput;
    JTextField usernameSignupInput;
    JTextField passwordSignupInput;
    JTextField userSearchInput;
    JTextArea friendList;
    JTextArea newsFeed;

    /* action listener for buttons */
    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == loginButton) {
                loginOrSignupFrame.setVisible(false);
                loginFrame.setVisible(true);
            }

            if (e.getSource() == signUpButton) {
                loginOrSignupFrame.setVisible(false);
                signupFrame.setVisible(true);
            }

            if (e.getSource() == loginAttemptButton) {
                if (UserDatabase.searchUser(usernameInput.getText()).getUsername().equals(usernameInput.getText())) {
                    JOptionPane.showMessageDialog(null, "Success!", "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                    // THIS IS WHERE WE WOULD DIRECT THEM THROUGH INTO THE MAIN MENU WHICH WOULD CONTAIN
                    // THE FEED, USER SEARCH, FRIENDS LISTS, ETC.
                    // FOR NOW, I JUST MADE IT END THE PROGRAM, PLEASE REMOVE LATER

                } else {
                    JOptionPane.showMessageDialog(null, "Wrong User/Password!", "Fail",
                            JOptionPane.ERROR_MESSAGE);
                    System.exit(0);
                }
                loginFrame.setVisible(false);
                mainMenuFrame.setVisible(true);
            }

            if (e.getSource() == accountCreationButton) {
                JOptionPane.showMessageDialog(null, "User Created with Username: " + usernameSignupInput.getText() + " and Password: " + passwordSignupInput.getText(), "Fail",
                        JOptionPane.INFORMATION_MESSAGE);

                if(UserDatabase.searchUser(usernameInput.getText()) != null){
                    JOptionPane.showMessageDialog(null, "User Already Exists, Try Again!", "Error", JOptionPane.ERROR_MESSAGE);
                    System.exit(0);
                }

                JOptionPane.showMessageDialog(null, "Success!", "Success",
                        JOptionPane.INFORMATION_MESSAGE);

                signupFrame.setVisible(false);
                loginOrSignupFrame.setVisible(true);
                //content frame needs to be set up, and shown here
            }

        }
    };

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new ClientGUI());
    }





    public void run() {
        /* set up JFrame */
        loginOrSignupFrame = new JFrame("User Login/Signup");
        Container loginOrSignupFrameContent = loginOrSignupFrame.getContentPane();
        loginOrSignupFrameContent.setLayout(new BorderLayout());

        loginFrame = new JFrame("User Login");
        Container loginContent = loginFrame.getContentPane();
        loginContent.setLayout(new BorderLayout());

        signupFrame = new JFrame("User Signup");
        Container signupContent = signupFrame.getContentPane();
        signupContent.setLayout(new BorderLayout());


        loginButton = new JButton("Login");
        loginButton.addActionListener(actionListener);
        signUpButton = new JButton("Sign Up");
        signUpButton.addActionListener(actionListener);
        JLabel welcomeMessage = new JLabel("Welcome to our Social Media Service!");
        welcomeMessage.setFont(new Font("Serif", Font.BOLD, 25));


        loginOrSignupFrame.setSize(600, 150);
        loginOrSignupFrame.setBackground(Color.white);
        loginOrSignupFrame.setLocationRelativeTo(null);
        loginOrSignupFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginOrSignupFrame.setVisible(true);

        JPanel loginOrSignupMiddlePanel = new JPanel();
        loginOrSignupMiddlePanel.add(loginButton);
        loginOrSignupMiddlePanel.add(signUpButton);
        loginOrSignupFrameContent.add(loginOrSignupMiddlePanel, BorderLayout.CENTER);

        JPanel loginOrSignupTopPanel = new JPanel();
        loginOrSignupTopPanel.add(welcomeMessage);
        loginOrSignupFrameContent.add(loginOrSignupTopPanel, BorderLayout.NORTH);

        loginFrame.setSize(600, 200);
        loginFrame.setBackground(Color.white);
        loginFrame.setLocationRelativeTo(null);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setVisible(false);

        JLabel loginInstructions = new JLabel("Please enter your username and password.");
        loginInstructions.setFont(new Font("Serif", Font.BOLD, 25));
        usernameInput = new JTextField(10);
        passwordInput = new JTextField(10);
        JLabel usernameLabel = new JLabel("Username: ");
        JLabel passwordLabel = new JLabel("Password: ");
        loginAttemptButton = new JButton("Login");
        loginAttemptButton.addActionListener(actionListener);

        JPanel loginTopPanel = new JPanel();
        loginTopPanel.add(loginInstructions);
        loginFrame.add(loginTopPanel, BorderLayout.NORTH);

        JPanel loginMiddlePanel = new JPanel();
        loginMiddlePanel.add(usernameLabel);
        loginMiddlePanel.add(usernameInput);
        loginMiddlePanel.add(passwordLabel);
        loginMiddlePanel.add(passwordInput);
        loginMiddlePanel.add(loginAttemptButton);
        loginFrame.add(loginMiddlePanel, BorderLayout.CENTER);


        signupFrame.setSize(600, 175);
        signupFrame.setBackground(Color.white);
        signupFrame.setLocationRelativeTo(null);
        signupFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        JLabel signupInfo = new JLabel();
        JLabel usernameSignupInputText = new JLabel();
        usernameSignupInputText.setText("Username: ");
        JLabel passwordSignupInputText = new JLabel();
        passwordSignupInputText.setText("Password: ");
        signupInfo.setText("Input the username and password of the account you would like to create.");
        signupInfo.setFont(new Font("Serif", Font.BOLD, 15));
        accountCreationButton = new JButton("Create Account");
        accountCreationButton.addActionListener(actionListener);
        usernameSignupInput = new JTextField(10);
        passwordSignupInput = new JTextField(10);

        JPanel signupTopPanel = new JPanel();
        signupTopPanel.add(signupInfo);

        signupFrame.add(signupTopPanel, BorderLayout.NORTH);

        JPanel signupMiddlePanel = new JPanel();
        signupMiddlePanel.add(usernameSignupInputText);
        signupMiddlePanel.add(usernameSignupInput);
        signupMiddlePanel.add(passwordSignupInputText);
        signupMiddlePanel.add(passwordSignupInput);
        signupMiddlePanel.add(accountCreationButton);
        signupFrame.add(signupMiddlePanel, BorderLayout.CENTER);

        mainMenuFrame = new JFrame("Main Menu");
        Container mainMenuContent = mainMenuFrame.getContentPane();
        mainMenuContent.setLayout(new BorderLayout());

        mainMenuFrame.setVisible(false);
        mainMenuFrame.setBackground(Color.white);
        mainMenuFrame.setSize(1000,750);

        JPanel mainMenuLeftFriendList = new JPanel();
        friendList = new JTextArea();
        friendList.setText("Friends List: \n Friend1 \n Friend2 \n Friend3");
        friendList.setEditable(false);
        friendList.setFont(new Font("Serif", Font.BOLD, 35));
        mainMenuLeftFriendList.add(friendList);
        mainMenuFrame.add(mainMenuLeftFriendList, BorderLayout.WEST);

        userSearchInput = new JTextField(10);
        userSearchInput.setFont(new Font("Serif", Font.BOLD, 35));
        JLabel userSearchHint = new JLabel("User Search: ");
        JPanel mainMenuUserSearchPanel = new JPanel();
        mainMenuUserSearchPanel.add(userSearchHint);
        mainMenuUserSearchPanel.add(userSearchInput);
        mainMenuFrame.add(mainMenuUserSearchPanel, BorderLayout.NORTH);

        userSearchButton = new JButton("Search");
        userSearchButton.addActionListener(actionListener);
        mainMenuUserSearchPanel.add(userSearchButton);

        newsFeed = new JTextArea();
        newsFeed.setEditable(false);
        newsFeed.setFont(new Font("Serif", Font.BOLD, 25));
        newsFeed.setText("This is where the feed will go \n Example: \n \n Post 1 - by username \n \n Post text goes here \n \n Post 2 - by username2 \n \n Other post text goes here");

        JPanel middleMainMenuPanel = new JPanel();
        middleMainMenuPanel.setLayout(new BorderLayout());
        middleMainMenuPanel.add(newsFeed);
        mainMenuFrame.add(middleMainMenuPanel, BorderLayout.CENTER);
    }
}
