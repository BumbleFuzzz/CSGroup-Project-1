# CSGroup-Project-1
## Overview
This project is a console-based social media platform, developed in Java, which is intended to simulate key features of popular social networks. The platform supports functionalities like user account creation, friend requests, blocking users, creating and managing posts, commenting, and displaying a personalized news feed. The project was implemented using a client-server architecture where all the computation occurs on the server, while users interact through the client. 

## Brightspace and Vocareum Submisson
- Asher Earnhart submitted Phase 1 workspace to Vocareum
- Austin Napier submitted Phase 2 workspace to Vocareum
- Yog Trivedi submitted Phase 3 workspace to Vocareum

## Instructions on How to Compile and Run the Project

### Prerequisites:
- Java Development Kit (JDK) installed.
- A compatible IDE such as IntelliJ IDEA or Eclipse.
- JUnit5 to run the unit tests for the classes.

### Compilation and Execution Instructions:
In Phase 1, we focused on building the backend of our social media platform, which included creating a thread-safe user database and implementing JUnit test cases for core components. During this phase, we did not have a main method to show interactions between the server, clients, and database.

Next, in Phase 2, we implemented the server and console-based client to facilitate interaction. You can start the server by running the Server.java class. Once the server is running, execute the Client.java class to access the application. If you want to simulate multiple users interacting simultaneously, you can run multiple instances of Client.java. For remote testing, use IntelliJ’s "Code with Me" feature to share your workspace and allow others to connect and execute the client from their terminals.

For Phase 3, we introduced a graphical user interface (GUI) for an improved user experience. To use the GUI, run the ClientGUI.java class after starting the server. The GUI includes features such as profile management, a newsfeed, and friend interactions. You can simulate multiple clients by running ClientGUI.java in separate terminals or IDE instances. All scripts can be found in the src directory.

### Application Flow and Usage Instructions
The application starts with an initial menu that allows you to:

#### 1. Sign In or Create a New Account:
- When you first run the program, you will be prompted to either Sign In using an existing account or Create a New Account.
- If the database is empty, you must create a new account. Each account requires a unique username and password.

#### 2. Main Menu:
- Once logged in, you will be provided with the following options:
    - Modify your profile details, such as profile name or description.
    - Look for other users using their login username.
    - Log out of the current session and exit the program.

#### 3. Edit Profile:
- In this menu, you can:
    - View and update your profile details, including your display name, biography, friends list, and blocked users.
    - Modify the Profile Name or Profile Description.

#### 4. Search for Users:
- To search for a user, use their login username. The profile name may change, but the login username remains consistent.
- Once you find a user, you will have a new set of options:
    - Add/Remove Friend.
    - Block/Unblock User.
    - Go Back to the previous menu.

#### 5. Newsfeed
- You can view posts from friends in chronological order.
- You can interact with posts by upvoting, downvoting, commenting, or hiding them.

#### 6. Post and Comment Interactions:
- You can make posts, which will appear in their friends' feeds.
- Displays posts made by their friends.
- Be able to add, upvote, and delete comments on posts.

# Class Descriptions
This project is structured using multiple classes, each implementing an interface to ensure flexibility and modularity. Below are the descriptions of all the classes:

## User
The User class represents an individual user in the application. It stores data like the username, password, biography, friends list, and blocked users. Users can log in, add friends, and block/unblock other users.
- Key Fields: username, password, biography, friends, blockedUsers.
- Key Methods:
  - addFriend(String friend): Adds a friend to the user’s friend list.
  - blockUser(String userToBlock): Blocks a user and removes them from the friend list.
  - loginAttempt(String username, String password): Checks if the provided credentials match the user’s stored data.
- Tests:
Tested user creation, friend/block functionality, and login validation.

## UserDatabase
The UserDatabase class manages persistent storage of user data. It ensures that all user information is saved and retrievable across sessions.
- Key Fields: listOfUsers, currentDBFile.
- Key Methods:
    - addUser(User user): Adds a new user to the database.
    - createDatabaseFile(): Saves all user data to a file.
    - searchUser(String username): Finds a user in the database by their username.
- Tests:
Verified data persistence and proper functionality for user addition/removal.

## PostClass
The PostClass manages posts created by users. Each post includes details like a title, description, and timestamps, along with voting capabilities.
- Key Fields: postID, postTitle, postDescription, upVotes, downVotes.
- Key Methods:
    - upvote(), downvote(): Allow users to interact with posts.
    - createPostFile(): Saves post data to a file.
- Tests:
Verified post creation, interaction, and file storage.

## Comments
The Comments class enables users to add, upvote, or delete comments on posts.
- Key Fields: commentText, upvotes, downvotes.
- Key Methods:
    - appendCommentToPostFile(): Adds a comment to the post’s associated file.
    - deleteCommentFromPostFile(User commenter): Deletes comments made by the specified user.
- Tests:
Tested comment addition, deletion, and voting.

## NewsFeed
The NewsFeed class is responsible for displaying posts to users in a chronological order.
- Key Fields: allPosts, friendPosts, hiddenPosts.
- Key Methods:
    - displayFeed(): Shows posts from friends while filtering out hidden content.
    - addFriendPost(PostClass post): Adds a friend’s post to the feed.
- Tests:
Verified post aggregation and feed display.

## Server
The Server class processes client requests and manages user data. It is multi-threaded to handle multiple clients concurrently.
- Key Fields: userDatabase, serverSocket, activeUsers.
- Key Methods:
    - startServer(int port): Starts the server to listen for connections.
    - handleRequest(Object request, ObjectOutputStream outStream): Processes client requests and sends responses.
- Tests:
Ensured thread safety and proper handling of client interactions.

## Client and ClientGUI
The Client class provides the console-based interface, while ClientGUI offers a graphical interface for the application.
- Key Fields: socket, inStream, outStream.
- Key Methods:
    - sendRequest(Object request): Sends a request to the server.
    - closeConnection(): Closes the client’s connection to the server.
- Tests:
Verified smooth operations for both console and GUI interfaces.

# IO Testing
We conducted extensive testing to ensure that the application behaves as expected during normal and edge-case scenarios. Here’s how we approached testing for various parts of the system:

## 1. Login and Signup:

- We tested the login and signup processes thoroughly to ensure smooth user entry into the platform.
- The application successfully blocks the creation of accounts with duplicate usernames, showing the appropriate error message.
- Incorrect username or password inputs during login were handled gracefully, prompting the user to try again.

## 2. Profile Management:

- We verified that profile updates (e.g., changing name or biography) are reflected immediately in the database and across client sessions.
- Other users can see these updates instantly when searching for the profile again.

## 3. Friend and Block Management:

- Adding and removing friends worked seamlessly, with both users’ friend lists updating correctly.
- Blocking a user automatically removes them from the friend list, ensuring no further interaction is possible until they are unblocked.
- Unblocking worked as intended, allowing users to reestablish connections.

## 4. Newsfeed:

- Posts created by a user appeared on their friends’ newsfeeds in real-time.
- The feed displayed posts in chronological order, and hiding a post successfully removed it from view while retaining it on the backend.

## 5. Post and Comment Interactions:

- Upvoting, downvoting, and commenting on posts worked without any issues.
- Comments added to posts were saved correctly and visible to all users with access to the post.
- Users were able to delete their own comments, with the changes reflected immediately across all connected clients.

## 6. Multi-Client Scenarios:

- We tested the application by running multiple clients simultaneously. Actions like adding friends, creating posts, and commenting were synchronized across all clients without delays or errors.
- Abruptly closing a client did not disrupt the server or other active clients. Upon restarting the client, all data was intact, thanks to the persistent database.

## 7. Server Restarts:

- The server was restarted multiple times during testing to ensure that all user, post, and comment data persisted correctly.
- After a restart, clients reconnected to the server without losing any data or functionality.

## 8. Error Handling:

- Attempts to perform invalid actions, such as friending a blocked user or posting without logging in, were met with appropriate error messages.
- Unexpected inputs, such as empty fields or special characters, were handled gracefully, ensuring the application remained stable.
