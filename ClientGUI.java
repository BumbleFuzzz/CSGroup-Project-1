import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

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
    JFrame createPostFrame;
    JFrame createCommentFrame;
    JFrame commentFrame;
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
    JButton confirmPostCreationButton;
    JButton confirmCommentCreationButton;
    JButton upvoteButton;
    JButton downvoteButton;
    JButton openCommentsButton;
    JButton commentMainMenue;
    JButton upvoteCommentButton;
    JButton downvoteCommentButton;
    JButton deleteCommentButton;
    JButton addCommentButton;
    JTextField usernameInput;
    JTextField passwordInput;
    JTextField usernameSignupInput;
    JTextField passwordSignupInput;
    JTextField userSearchInput;
    JTextField createPostTitle;
    JTextField createPostDescription;
    JTextField createCommentDescription;
    JTextField upvoteDownvoteInput;
    JTextField openCommentsInput;
    JTextField upvoteDownvoteCommentInput;
    JTextField deleteCommentInput;
    JTextArea friendList;
    JTextArea newsFeed;
    JTextArea profileName;
    JTextArea profileBio;
    JTextArea otherProfileName;
    JTextArea otherProfileBio;
    JTextArea commentsArea;
    User loggedInUser;
    NewsFeed userNewsFeed = new NewsFeed();
    UserDatabase centralUserDatabase = new UserDatabase();
    String friendButtonContents;
    int currentPost;
    String currentPoster;
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
                newsFeedPopulateOnStartup();
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
                if (centralUserDatabase.searchUser(userSearchInput.getText()) != null && !userSearchInput.getText().equals(loggedInUser.getUsername()) && !userSearchInput.getText().trim().equals("") && !loggedInUser.isBlocked(userSearchInput.getText())) {
                    mainMenuFrame.setVisible(false);
                    User targetUser = centralUserDatabase.searchUser(userSearchInput.getText());
                    otherProfileName.setText("Profile:\n" + targetUser.getUsername());
                    otherProfileBio.setText("Biography:\n" + targetUser.getBiography());
                    if (loggedInUser.isFriend(userSearchInput.getText())) {
                        friend.setText("Unfriend");
                    } else {
                        friend.setText("Friend");
                    }
                    otherProfileFrame.setVisible(true);
                } else if (centralUserDatabase.searchUser(userSearchInput.getText()) == null || userSearchInput.getText().trim().equals("")) {
                    JOptionPane.showMessageDialog(null, "User does not exist!", "Fail",
                            JOptionPane.ERROR_MESSAGE);
                } else if (userSearchInput.getText().equals(loggedInUser.getUsername())) {
                    JOptionPane.showMessageDialog(null, "You can not search yourself!", "Fail",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "You have this user blocked!", "Fail",
                            JOptionPane.ERROR_MESSAGE);
                }
            }

            if (e.getSource() == friend) {
                if (!loggedInUser.isFriend(userSearchInput.getText())) {
                    loggedInUser.addFriend(userSearchInput.getText());
                    centralUserDatabase.addUser(loggedInUser);
                    centralUserDatabase.updateDatabaseFile();
                } else {
                    loggedInUser.removeFriend(userSearchInput.getText());
                    centralUserDatabase.addUser(loggedInUser);
                    centralUserDatabase.updateDatabaseFile();
                }
                otherProfileFrame.setVisible(false);
                newsFeedPopulateOnStartup();
                mainMenuPopulate();
                mainMenuFrame.setVisible(true);
            }

            if (e.getSource() == createPostButton) {
                createPostFrame.setVisible(true);
            }

            if (e.getSource() == addCommentButton) {
                createCommentFrame.setVisible(true);
            }

            if (e.getSource() == confirmCommentCreationButton) {
                Comments comment = new Comments(currentPost, loggedInUser.getUsername(), createCommentDescription.getText());
                comment.appendCommentToPostFile();
                commentsPopulate();
                commentFrame.setVisible(false);
                commentFrame.setVisible(true);
            }

            if (e.getSource() == confirmPostCreationButton) {
                PostClass newPost = new PostClass(loggedInUser.getUsername(), createPostTitle.getText(), createPostDescription.getText(), 0, 0);
                newPost.createPostFile();
                try {
                    File newFile = new File("comments/" + newPost.getPostID() + ".txt");
                    if (newFile.createNewFile()) {
                        System.out.println("=/aeju");
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                userNewsFeed.addPost(newPost);
                JOptionPane.showMessageDialog(null, "Success!", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                createPostFrame.setVisible(false);
                mainMenuFrame.setVisible(false);
                newsFeedPopulateOnStartup();
                mainMenuPopulate();
                mainMenuFrame.setVisible(true);
            }

            if (e.getSource() == block) {
                int response = JOptionPane.showConfirmDialog(null, "WARNING: This can not be undone. Are you sure you want to continue?", "Order Form", JOptionPane.YES_NO_OPTION);
                if (response == JOptionPane.YES_OPTION) {
                    loggedInUser.blockUser(userSearchInput.getText());
                    loggedInUser.removeFriend(userSearchInput.getText());
                    centralUserDatabase.addUser(loggedInUser);
                    centralUserDatabase.updateDatabaseFile();
                }
                otherProfileFrame.setVisible(false);
                newsFeedPopulateOnStartup();
                mainMenuPopulate();
                mainMenuFrame.setVisible(true);
            }

            if (e.getSource() == downvoteButton) {
                String postFile = "posts/post-" + upvoteDownvoteInput.getText() + ".txt";
                Path filePath = Paths.get(postFile);
                ArrayList<String> lines = new ArrayList<>();

                try {
                    // Read all lines into a list
                    lines = (ArrayList<String>) Files.readAllLines(filePath);

                    // Check if the file has at least 6 lines
                    if (lines.size() >= 6) {
                        // Parse the 6th line to an integer, increment by 1, and update the line
                        try {
                            int sixthLineValue = Integer.parseInt(lines.get(5).trim()); // 6th line is at index 5
                            lines.set(5, String.valueOf(sixthLineValue + 1));
                        } catch (NumberFormatException s) {
                            return;
                        }
                    } else {
                        return;
                    }

                    // Write the updated lines back to the file
                    Files.write(filePath, lines);
                } catch (IOException s) {
                    System.out.println("An error occurred while modifying the file: " + s.getMessage());
                }
                mainMenuFrame.setVisible(false);
                newsFeedPopulateOnStartup();
                mainMenuPopulate();
                mainMenuFrame.setVisible(true);
            }

            if (e.getSource() == upvoteButton) {
                String postFile = "posts/post-" + upvoteDownvoteInput.getText() + ".txt";
                Path filePath = Paths.get(postFile);
                ArrayList<String> lines = new ArrayList<>();

                try {
                    // Read all lines into a list
                    lines = (ArrayList<String>) Files.readAllLines(filePath);

                    // Check if the file has at least 6 lines
                    if (lines.size() >= 5) {
                        // Parse the 6th line to an integer, increment by 1, and update the line
                        try {
                            int fifthLineValue = Integer.parseInt(lines.get(4).trim()); // 6th line is at index 5
                            lines.set(4, String.valueOf(fifthLineValue + 1));
                        } catch (NumberFormatException s) {
                            return;
                        }
                    } else {
                        return;
                    }

                    // Write the updated lines back to the file
                    Files.write(filePath, lines);
                } catch (IOException s) {
                    System.out.println("An error occurred while modifying the file: " + s.getMessage());
                }
                mainMenuFrame.setVisible(false);
                newsFeedPopulateOnStartup();
                mainMenuPopulate();
                mainMenuFrame.setVisible(true);
            }

            if (e.getSource() == openCommentsButton) {
                currentPost = Integer.parseInt(openCommentsInput.getText());
                mainMenuFrame.setVisible(false);
                commentsPopulate();
                commentFrame.setVisible(true);
            }

            if (e.getSource() == commentMainMenue) {
                commentFrame.setVisible(false);
                newsFeedPopulateOnStartup();
                mainMenuPopulate();
                mainMenuFrame.setVisible(true);
            }

            if (e.getSource() == downvoteCommentButton) {
                String postFile = "comments/" + currentPost + ".txt";
                Path filePath = Paths.get(postFile);
                ArrayList<String> lines = new ArrayList<>();

                try {
                    // Read all lines into a list
                    lines = new ArrayList<>(Files.readAllLines(filePath));
                    for (int i = 0; i < lines.size(); i++) {
                        String line = lines.get(i);
                        String[] parts = line.split(",");

                        // Compare strings using .equals()
                        if (parts[0].equals(upvoteDownvoteCommentInput.getText())) {
                            // Update the relevant field
                            parts[4] = Integer.toString(Integer.parseInt(parts[4]) + 1);

                            // Rebuild the line and update the list
                            lines.set(i, String.join(",", parts));
                        }
                    }

                    // Write the updated lines back to the file
                    Files.write(filePath, lines);
                } catch (IOException s) {
                    System.out.println("An error occurred while modifying the file: " + s.getMessage());
                }

                commentFrame.setVisible(false);
                commentsPopulate();
                commentFrame.setVisible(true);
            }

            if (e.getSource() == upvoteCommentButton) {
                String postFile = "comments/" + currentPost + ".txt";
                Path filePath = Paths.get(postFile);
                ArrayList<String> lines = new ArrayList<>();

                try {
                    // Read all lines into a list
                    lines = new ArrayList<>(Files.readAllLines(filePath));
                    for (int i = 0; i < lines.size(); i++) {
                        String line = lines.get(i);
                        String[] parts = line.split(",");

                        // Compare strings using .equals()
                        if (parts[0].equals(upvoteDownvoteCommentInput.getText())) {
                            // Update the relevant field
                            parts[3] = Integer.toString(Integer.parseInt(parts[3]) + 1);

                            // Rebuild the line and update the list
                            lines.set(i, String.join(",", parts));
                        }
                    }

                    // Write the updated lines back to the file
                    Files.write(filePath, lines);
                } catch (IOException s) {
                    System.out.println("An error occurred while modifying the file: " + s.getMessage());
                }

                commentFrame.setVisible(false);
                commentsPopulate();
                commentFrame.setVisible(true);
            }

            if (e.getSource() == deleteCommentButton) {
                String postFile = "comments/" + currentPost + ".txt";
                Path filePath = Paths.get(postFile);
                ArrayList<String> lines = new ArrayList<>();

                try {
                    // Read all lines into a list
                    lines = new ArrayList<>(Files.readAllLines(filePath));
                    for (int i = 0; i < lines.size(); i++) {
                        String line = lines.get(i);
                        String[] parts = line.split(",");

                        // Compare strings using .equals()
                        if (parts[1].equals(loggedInUser.getUsername())) {
                            // Update the relevant field
                            commentsArea.setText("");


                        }
                    }

                } catch (IOException s) {
                    System.out.println("An error occurred while modifying the file: " + s.getMessage());
                }
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
        // Friends List
        if (loggedInUser.getFriends() == null || loggedInUser.getFriends().isEmpty()) {
            friendList.setText("Friends:\nNo friended users found!");
        } else {
            friendList.setText("Friends:\n" + String.join("\n", loggedInUser.getFriends()));
        }

        // News Feed
        if (userNewsFeed.getAllPosts() == null || userNewsFeed.getAllPosts().isEmpty()) {
            newsFeed.setText("No friend posts found!");
        } else {
            ArrayList<User> friends = new ArrayList<>();
            for (User potentialFriend : centralUserDatabase.getUsers()) {
                if (loggedInUser.getFriends().contains(potentialFriend.getUsername())) {
                    friends.add(potentialFriend);
                }
            }

            for (PostClass post : userNewsFeed.getAllPosts()) {
                try (BufferedReader br = new BufferedReader(new FileReader(post.filename))) {
                    String postContents = "";
                    String line = br.readLine();
                    String secondLine = br.readLine();
                    if ((loggedInUser.getFriends().contains(secondLine) && !loggedInUser.getBlockedUsers().contains(secondLine)) || loggedInUser.getUsername().contains(secondLine)) {
                        postContents += "Post ID: " + post.getPostID() + "\n";
                        postContents += "Post by: " + post.getOriginalPoster() + "\n";
                        line = br.readLine();
                        for (int i = 0; i < 2; i++) {
                            postContents += line + "\n";
                            line = br.readLine();
                        }
                        postContents += "Upvotes: " + line + "  ";
                        postContents += "Downvotes: " + br.readLine() + "\n +=============================+ \n";
                    }
                    newsFeed.append(postContents);
                } catch(IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    private void commentsPopulate() {
        commentsArea.setText("");
        String commentFile = "comments/" + openCommentsInput.getText() + ".txt";
        Path filePath = Paths.get(commentFile);
        ArrayList<String> lines = new ArrayList<>();

        try {
            // Read all lines into a list
            lines = (ArrayList<String>) Files.readAllLines(filePath);
            for (String line : lines) {
                String[] parts = line.split(",");
                String commentID = parts[0];
                String commenter = parts[1];
                String comment = parts[2];
                String upvotes = parts[3];
                String downvotes = parts[4];
                commentsArea.append("Comment ID: " + commentID + "\n");
                commentsArea.append("Commenter: " + commenter + "\n");
                commentsArea.append(comment + "\n");
                commentsArea.append("Upvotes: " + upvotes + "\n");
                commentsArea.append("Downvotes: " + downvotes + "\n +====+ \n");

            }

        } catch (IOException s) {
            System.out.println("An error occurred while modifying the file: " + s.getMessage());
        }
    }

    private void newsFeedPopulateOnStartup() {
        String folderPath = "posts";
        userNewsFeed.clearFeed();
        newsFeed.setText("");
        try (Stream<Path> paths = Files.walk(Path.of(folderPath))) {
            paths.filter(Files::isRegularFile).forEach(filePath -> {
                        try (BufferedReader br = new BufferedReader(new FileReader(filePath.toFile()))) {
                            String fileInfo = "";
                            String line = br.readLine();
                            while (line != null) {
                                fileInfo += line + ",";
                                line = br.readLine();
                            }

                            if (fileInfo.isEmpty() || fileInfo.equals(",")) {
                                System.out.println("=/asce");
                            } else {
                                String[] fileInfoSplit = fileInfo.split(",");

                                User foundUser = new User("blank", "blank", "blank");
                                for (User user : centralUserDatabase.getUsers()) {
                                    if (user.getUsername().equals(fileInfoSplit[1])) {
                                        foundUser = user;
                                    }
                                }
                                PostClass postToAdd = new PostClass(Integer.parseInt(fileInfoSplit[0]), foundUser.getUsername(), fileInfoSplit[2], fileInfoSplit[3], 0 , 0);
                                postToAdd.filename = String.valueOf(filePath);
                                userNewsFeed.addPost(postToAdd);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
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

        upvoteDownvoteInput = new JTextField();
        openCommentsInput = new JTextField(10);
        upvoteButton = new JButton("Upvote");
        upvoteButton.addActionListener(actionListener);
        downvoteButton = new JButton("Downvote");
        downvoteButton.addActionListener(actionListener);

        openCommentsButton = new JButton("Open Comments");
        openCommentsButton.addActionListener(actionListener);

        JLabel upvoteDownvoteHint = new JLabel("Put a post ID here, then choose to upvote or downvote it.");
        JLabel openCommentsHint = new JLabel("Put a post ID here in order to open its comment section");

        commentFrame = new JFrame("Comments");
        Container commentContent = commentFrame.getContentPane();
        commentContent.setLayout(new BorderLayout());

        commentFrame.setVisible(false);
        commentFrame.setBackground(Color.white);
        commentFrame.setSize(1000,750);

        JPanel commentLeftPannel = new JPanel();
        commentLeftPannel.setLayout(new BoxLayout(commentLeftPannel, BoxLayout.Y_AXIS));
        upvoteCommentButton = new JButton("Upvote");
        downvoteCommentButton = new JButton("Downvote");
        deleteCommentButton = new JButton("Delete");
        addCommentButton = new JButton("Add Comment");
        upvoteDownvoteCommentInput = new JTextField();
        deleteCommentInput = new JTextField();
        commentLeftPannel.add(upvoteCommentButton);
        commentLeftPannel.add(downvoteCommentButton);
        JLabel upvoteDownvoteCommentHint = new JLabel("Put a comment ID here, then choose to upvote or downvote it.");
        commentLeftPannel.add(upvoteDownvoteCommentHint);
        commentLeftPannel.add(upvoteDownvoteCommentInput);
        JLabel deleteCommentHint = new JLabel("Put a comment ID here, then choose to delete it.");
        commentLeftPannel.add(deleteCommentHint);
        commentLeftPannel.add(deleteCommentInput);
        commentLeftPannel.add(deleteCommentButton);
        commentLeftPannel.add(addCommentButton);
        commentFrame.add(commentLeftPannel, BorderLayout.EAST);

        JPanel commentRightPannel = new JPanel();
        commentsArea = new JTextArea();
        commentsArea.setEditable(false);
        commentsArea.setLineWrap(true);
        commentRightPannel.add(commentsArea);
        commentFrame.add(commentRightPannel, BorderLayout.CENTER);

        commentMainMenue = new JButton("Main Menu");
        commentMainMenue.addActionListener(actionListener);
        upvoteCommentButton.addActionListener(actionListener);
        downvoteCommentButton.addActionListener(actionListener);
        deleteCommentButton.addActionListener(actionListener);
        addCommentButton.addActionListener(actionListener);
        commentLeftPannel.add(commentMainMenue);

        JPanel mainMenuLeftFriendList = new JPanel();
        mainMenuLeftFriendList.setLayout(new BoxLayout(mainMenuLeftFriendList, BoxLayout.Y_AXIS));
        friendList = new JTextArea();
        friendList.setText("Friends List:");
        friendList.setEditable(false);
        friendList.setFont(new Font("Serif", Font.BOLD, 35));
        mainMenuLeftFriendList.add(friendList);
        mainMenuLeftFriendList.add(upvoteDownvoteHint);
        mainMenuLeftFriendList.add(upvoteDownvoteInput);
        mainMenuLeftFriendList.add(upvoteButton);
        mainMenuLeftFriendList.add(downvoteButton);
        mainMenuLeftFriendList.add(openCommentsHint);
        mainMenuLeftFriendList.add(openCommentsInput);
        mainMenuLeftFriendList.add(openCommentsButton);
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

        newsFeed = new JTextArea();
        createPostButton = new JButton("Create Post");
        createPostButton.addActionListener(actionListener);
        newsFeed.setEditable(false);
        newsFeed.setFont(new Font("Serif", Font.BOLD, 25));

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
        friend = new JButton(friendButtonContents);
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

        createPostFrame = new JFrame("Create Post");
        createPostFrame.setSize(750,300);
        Container createPostFrameContent = createPostFrame.getContentPane();
        createPostFrameContent.setLayout(new BorderLayout());
        createPostFrame.setVisible(false);

        createPostTitle = new JTextField();
        createPostDescription = new JTextField();

        createPostTitle.setText("Post title goes here!");
        createPostTitle.setFont(new Font("Serif", Font.PLAIN,50));
        createPostDescription.setText("Post description goes here!");
        createPostDescription.setFont(new Font("Serif", Font.PLAIN,35));

        confirmPostCreationButton = new JButton("Create Post");
        confirmPostCreationButton.addActionListener(actionListener);

        JPanel createPostCentralPanel = new JPanel();
        createPostCentralPanel.add(createPostTitle);
        createPostCentralPanel.add(createPostDescription);
        createPostCentralPanel.add(confirmPostCreationButton);

        createPostFrame.add(createPostCentralPanel, BorderLayout.CENTER);

        createCommentFrame = new JFrame("Create Post");
        createCommentFrame.setSize(750,300);
        Container createCommentFrameContent = createCommentFrame.getContentPane();
        createCommentFrameContent.setLayout(new BorderLayout());
        createCommentFrame.setVisible(false);

        createCommentDescription = new JTextField();

        createCommentDescription.setText("Comment description goes here!");
        createCommentDescription.setFont(new Font("Serif", Font.PLAIN,35));

        confirmCommentCreationButton = new JButton("Create Comment");
        confirmCommentCreationButton.addActionListener(actionListener);

        JPanel createCommentCentralPanel = new JPanel();
        createCommentCentralPanel.add(createCommentDescription);
        createCommentCentralPanel.add(confirmCommentCreationButton);

        createCommentFrame.add(createCommentCentralPanel, BorderLayout.CENTER);

    }
}
