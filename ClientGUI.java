import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * GUI for the Client of the Social Media Program
 *
 * @author Austin Napier
 * @version 12/07/2024
 */

public class ClientGUI implements Runnable {
    JFrame loginOrSignupFrame;
    JFrame loginFrame;
    JFrame signupFrame;
    JFrame mainMenuFrame;
    JFrame profileFrame;
    JFrame otherProfileFrame;
    JFrame postFrame;
    JButton loginButton;
    JButton signUpButton;
    JButton loginAttemptButton;
    JButton accountCreationButton;
    JButton userSearchButton;
    JButton profileButton;
    JButton editBioButton;
    JButton ownProfileMainMenuButton;
    JButton otherProfileMainMenuButton;
    JButton block;
    JButton friend;
    JButton createPostButton;
    JTextField usernameInput;
    JTextField passwordInput;
    JTextField usernameSignupInput;
    JTextField passwordSignupInput;
    JTextField userSearchInput;
    JTextArea friendList;
    JPanel newsFeed;
    JTextArea profileName;
    JTextArea profileBio;
    JTextArea otherProfileName;
    JTextArea otherProfileBio;
    User loggedInUser;
    NewsFeed userNewsFeed = new NewsFeed();
    UserDatabase centralUserDatabase = new UserDatabase();

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
                if (centralUserDatabase.searchUser(usernameInput.getText()) != null && centralUserDatabase.searchUser(usernameInput.getText()).getUsername().equals(usernameInput.getText()) && centralUserDatabase.searchUser(usernameInput.getText()).getPassword().equals(passwordInput.getText())) {
                    JOptionPane.showMessageDialog(null, "Success!", "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                    // THIS IS WHERE WE WOULD DIRECT THEM THROUGH INTO THE MAIN MENU WHICH WOULD CONTAIN
                    // THE FEED, USER SEARCH, FRIENDS LISTS, ETC.
                    // FOR NOW, I JUST MADE IT END THE PROGRAM, PLEASE REMOVE LATER
                    loggedInUser = centralUserDatabase.searchUser(usernameInput.getText());
                } else {
                    JOptionPane.showMessageDialog(null, "Wrong User/Password!", "Fail",
                            JOptionPane.ERROR_MESSAGE);
                    System.exit(0);
                }
                loginFrame.setVisible(false);
                mainMenuPopulate();
                mainMenuFrame.setVisible(true);
            }

            if (e.getSource() == accountCreationButton) {
                JOptionPane.showMessageDialog(null, "User Created with Username: " + usernameSignupInput.getText() + " and Password: " + passwordSignupInput.getText(), "Created User",
                        JOptionPane.INFORMATION_MESSAGE);

                if(centralUserDatabase.searchUser(usernameInput.getText()) != null && centralUserDatabase.searchUser(usernameInput.getText()).getUsername().equals(usernameSignupInput.getText())) {
                    JOptionPane.showMessageDialog(null, "User Already Exists, Try Again!", "Error", JOptionPane.ERROR_MESSAGE);
                    System.exit(0);
                }

                centralUserDatabase.addUser(new User(usernameSignupInput.getText(), passwordSignupInput.getText(), "Empty"));
                centralUserDatabase.createDatabaseFile();

                JOptionPane.showMessageDialog(null, "Success!", "Success",
                        JOptionPane.INFORMATION_MESSAGE);

                signupFrame.setVisible(false);
                loginOrSignupFrame.setVisible(true);
            }

            if (e.getSource() == profileButton) {
                mainMenuFrame.setVisible(false);
                profileName.setText("Profile:\n" + loggedInUser.getUsername());
                profileBio.setText("Biography:\n" + loggedInUser.getBiography());
                profileFrame.setVisible(true);
            }

            if (e.getSource() == ownProfileMainMenuButton) {
                profileFrame.setVisible(false);
                mainMenuPopulate();
                mainMenuFrame.setVisible(true);
            }

            if (e.getSource() == otherProfileMainMenuButton) {
                otherProfileFrame.setVisible(false);
                mainMenuPopulate();
                mainMenuFrame.setVisible(true);
            }

            if (e.getSource() == editBioButton) {
                profileFrame.setVisible(false);
                loggedInUser.setBiography(JOptionPane.showInputDialog(null, "Enter New Biography", "Edit Biography",
                        JOptionPane.PLAIN_MESSAGE));
                centralUserDatabase.addUser(loggedInUser);
                centralUserDatabase.updateDatabaseFile();
                ProfilePopulate();
                profileFrame.setVisible(true);
            }

            if (e.getSource() == userSearchButton) {
                if (centralUserDatabase.searchUser(userSearchInput.getText()) != null && !userSearchInput.getText().equals(loggedInUser.getUsername()) && !userSearchInput.equals("")) {
                    mainMenuFrame.setVisible(false);
                    User targetUser = centralUserDatabase.searchUser(userSearchInput.getText());
                    otherProfileName.setText("Profile:\n" + targetUser.getUsername());
                    otherProfileBio.setText("Biography:\n" + targetUser.getBiography());
                    otherProfileFrame.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "User does not exist or you can not search yourself!", "Fail",
                            JOptionPane.ERROR_MESSAGE);
                }
            }

            if (e.getSource() == friend) {
                if (!loggedInUser.isFriend(userSearchInput.getText())) {
                    loggedInUser.addFriend(userSearchInput.getText());
                    centralUserDatabase.addUser(loggedInUser);
                    centralUserDatabase.updateDatabaseFile();
                }
                otherProfileFrame.setVisible(false);
                mainMenuPopulate();
                mainMenuFrame.setVisible(true);
            }

        }
    };

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new ClientGUI());
    }

    /*
    This helper method needs to be called every time the user is directed to the
    main menu, or every time you need to refresh the main menu for the user
    */
    private void mainMenuPopulate() {
        // Clear previous components
        newsFeed.removeAll();
        newsFeed.revalidate();
        newsFeed.repaint();

        // Friends List
        if (loggedInUser.getFriends() == null || loggedInUser.getFriends().isEmpty()) {
            friendList.setText("Friends:\nNo friended users found!");
        } else {
            friendList.setText("Friends:\n" + String.join("\n", loggedInUser.getFriends()));
        }

        // News Feed
        if (userNewsFeed.getAllPosts() == null || userNewsFeed.getAllPosts().isEmpty()) {
            JLabel noPostsLabel = new JLabel("No friend posts found!");
            newsFeed.add(noPostsLabel);
        } else {
            ArrayList<User> friends = new ArrayList<>();
            for (User potentialFriend : centralUserDatabase.getUsers()) {
                if (loggedInUser.getFriends().contains(potentialFriend.getUsername())) {
                    friends.add(potentialFriend);
                }
            }

            for (PostClass post : userNewsFeed.getAllPosts()) {
                if (friends.contains(post.getOriginalPoster())) {
                    // Create a button for each post
                    JButton postButton = new JButton(post.getPostTitle());
                    postButton.setToolTipText("<html><b>By:</b> " + post.getOriginalPoster() +
                            "<br><b>Description:</b> " + post.getPostDescription() + "</html>");

                    // Add action listener for button click
                    postButton.addActionListener(e -> {
                        JOptionPane.showMessageDialog(newsFeed,
                                "Title: " + post.getPostTitle() +
                                        "\nBy: " + post.getOriginalPoster() +
                                        "\n\n" + post.getPostDescription(),
                                "Post Details", JOptionPane.INFORMATION_MESSAGE);
                    });

                    // Add the button to the panel
                    newsFeed.add(postButton);
                }
            }
        }

        // Refresh the panel after adding components
        newsFeed.revalidate();
        newsFeed.repaint();
    }

    private void ProfilePopulate() {
        profileName.setText("Profile:\n" + loggedInUser.getUsername());
        profileBio.setText("Biography:\n" + loggedInUser.getBiography());
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
        JLabel welcomeMessage = new JLabel("Welcome to FriendFusion!");
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
        friendList.setText("Friends List:");
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

        profileButton = new JButton("My Profile");
        profileButton.addActionListener(actionListener);
        mainMenuUserSearchPanel.add(profileButton);

        newsFeed = new JPanel();
        newsFeed.setLayout(new BoxLayout(newsFeed, BoxLayout.Y_AXIS));
        createPostButton = new JButton("Create Post");
        createPostButton.addActionListener(actionListener);
        newsFeed.setFont(new Font("Serif", Font.BOLD, 25));
        JScrollPane newsFeedScrollPane = new JScrollPane(newsFeed);
        newsFeedScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        newsFeedScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        JPanel middleMainMenuPanel = new JPanel();
        middleMainMenuPanel.setLayout(new BorderLayout());
        middleMainMenuPanel.add(createPostButton, BorderLayout.BEFORE_FIRST_LINE);
        middleMainMenuPanel.add(newsFeed);
        mainMenuFrame.add(middleMainMenuPanel, BorderLayout.CENTER);

        profileFrame = new JFrame("Profile");
        Container profileContent = profileFrame.getContentPane();
        profileContent.setLayout(new BorderLayout());

        profileFrame.setVisible(false);
        profileFrame.setBackground(Color.white);
        profileFrame.setSize(1000,750);

        profileName = new JTextArea();
        profileName.setEditable(false);
        profileName.setFont(new Font("Serif", Font.BOLD, 20));

        ownProfileMainMenuButton = new JButton("Main Menu");
        ownProfileMainMenuButton.addActionListener(actionListener);
        editBioButton = new JButton("Edit");
        editBioButton.addActionListener(actionListener);

        JPanel profileNamePanel = new JPanel();
        profileNamePanel.setLayout(new BorderLayout());
        profileNamePanel.add(profileName);
        profileNamePanel.add(ownProfileMainMenuButton, BorderLayout.EAST);
        profileFrame.add(profileNamePanel, BorderLayout.NORTH);

        profileBio = new JTextArea();
        profileBio.setEditable(false);
        profileBio.setFont(new Font("Serif", Font.PLAIN,15));

        JPanel profileBioPanel = new JPanel();
        profileBioPanel.setLayout(new BorderLayout());
        profileBioPanel.add(editBioButton, BorderLayout.SOUTH);
        profileBioPanel.add(profileBio);
        profileFrame.add(profileBioPanel);

        //other user's profiles

        otherProfileFrame = new JFrame("Profile");
        Container otherProfileContent = otherProfileFrame.getContentPane();
        otherProfileContent.setLayout(new BorderLayout());

        otherProfileFrame.setVisible(false);
        otherProfileFrame.setBackground(Color.white);
        otherProfileFrame.setSize(1000,750);

        otherProfileName = new JTextArea();
        otherProfileName.setEditable(false);
        otherProfileName.setFont(new Font("Serif", Font.BOLD, 20));

        otherProfileMainMenuButton = new JButton("Main Menu");
        otherProfileMainMenuButton.addActionListener(actionListener);
        block = new JButton("Block");
        block.addActionListener(actionListener);
        friend = new JButton("Friend");
        friend.addActionListener(actionListener);

        JPanel otherProfileNamePanel = new JPanel();
        otherProfileNamePanel.setLayout(new BorderLayout());
        otherProfileNamePanel.add(otherProfileName);
        otherProfileNamePanel.add(otherProfileMainMenuButton, BorderLayout.EAST);
        otherProfileNamePanel.add(friend, BorderLayout.PAGE_END);
        otherProfileFrame.add(block, BorderLayout.SOUTH);
        otherProfileFrame.add(otherProfileNamePanel, BorderLayout.NORTH);

        otherProfileBio = new JTextArea();
        otherProfileBio.setEditable(false);
        otherProfileBio.setFont(new Font("Serif", Font.PLAIN,15));

        JPanel otherProfileBioPanel = new JPanel();
        otherProfileBioPanel.setLayout(new BorderLayout(100,100));
        otherProfileBioPanel.add(otherProfileBio);
        otherProfileFrame.add(otherProfileBioPanel);

        // Post GUI
        postFrame = new JFrame("Post");
        Container postContent = postFrame.getContentPane();
        postContent.setLayout(new BorderLayout());

    // Post details panel
        JPanel postDetailsPanel = new JPanel();
        postDetailsPanel.setLayout(new BoxLayout(postDetailsPanel, BoxLayout.Y_AXIS));
        postDetailsPanel.setBackground(Color.white);

    // Post title
        JLabel postTitleLabel = new JLabel();
        postTitleLabel.setFont(new Font("Serif", Font.BOLD, 16));
        postTitleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        postDetailsPanel.add(postTitleLabel);

    // Original poster
        JLabel postOriginalPosterLabel = new JLabel();
        postOriginalPosterLabel.setFont(new Font("Serif", Font.ITALIC, 14));
        postOriginalPosterLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        postDetailsPanel.add(postOriginalPosterLabel);

    // Post contents/description
        JTextArea postDescriptionArea = new JTextArea();
        postDescriptionArea.setLineWrap(true);
        postDescriptionArea.setWrapStyleWord(true);
        postDescriptionArea.setEditable(false);
        postDescriptionArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        postDescriptionArea.setBackground(Color.LIGHT_GRAY);
        postDescriptionArea.setAlignmentX(Component.LEFT_ALIGNMENT);
        postDetailsPanel.add(Box.createVerticalStrut(10)); // Add spacing
        postDetailsPanel.add(postDescriptionArea);

    // Buttons panel
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonsPanel.setBackground(Color.white);

    // Upvote button
        JButton upvoteButton = new JButton("Upvote");
        upvoteButton.addActionListener(e -> {
            // Handle upvote logic
            JOptionPane.showMessageDialog(postFrame, "Post upvoted!");
        });

    // Downvote button
        JButton downvoteButton = new JButton("Downvote");
        downvoteButton.addActionListener(e -> {
            // Handle downvote logic
            JOptionPane.showMessageDialog(postFrame, "Post downvoted!");
        });

    // Delete button
        JButton deleteButton = new JButton("Delete");
        deleteButton.setForeground(Color.RED);
        deleteButton.addActionListener(actionListener);

    // Add buttons to the panel
        buttonsPanel.add(upvoteButton);
        buttonsPanel.add(downvoteButton);
        buttonsPanel.add(deleteButton);

    // Add components to the main frame
        postContent.add(postDetailsPanel, BorderLayout.CENTER);
        postContent.add(buttonsPanel, BorderLayout.SOUTH);

    // Finalize frame settings
        postFrame.setVisible(false);
        postFrame.setBackground(Color.white);
        postFrame.setSize(500, 300);

    }
}