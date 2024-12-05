# CSGroup-Project-1
## Overview
This project is a console-based social media platform, developed in Java, which is intended to simulate key features of popular social networks. The platform supports functionalities like user account creation, friend requests, blocking users, creating and managing posts, commenting, and displaying a personalized news feed. The project was implemented using a client-server architecture where all the computation occurs on the server, while users interact through the client. 

## Brightspace and Vocareum Submisson
- Asher Earnhart submitted Phase 1 workspace to Vocareum
- Austin Napier submitted Phase 2 workspace to Vocareum

## Instructions on How to Compile and Run the Project

### Prerequisites:
- Java Development Kit (JDK) installed.
- A compatible IDE such as IntelliJ IDEA or Eclipse.
- JUnit5 to run the unit tests for the classes.

### Compilation and Execution Instructions:

#### Phase 1
In Phase 1, we focused on setting up user accounts, creating profiles, establishing relationships (friend/block), and implementing persistent storage. The functionality included:

- Created a persistent user database using .dat files.
- Developed interfaces for each class and corresponding JUnit test cases.
- Ensured thread safety using ConcurrentHashMap to allow the server to handle multiple clients safely.

For Phase 1, there is no dedicated main method to demonstrate the interaction between the server, clients, and the database.

#### Phase 2
In Phase 2, we implemented the interaction between clients and the server.

##### 1. Run the Server
Start compiling the Server.java class.
```java

javac Server.java
```

Run the server to start listening for incoming connections using:
```java

java Server
```

- The server will listen for incoming client connections on port 12345.
- The server can handle multiple clients simultaneously, creating a ClientHandler thread for each one.

##### 2. Run the Client
Next, compile the Client.java class.
```java

javac Client.java
```

Run the client to connect to the server using:
```java

java Client
```

- Run multiple instances of the Client.java class to simulate multiple users interacting with the server.
- To simulate the application working across multiple devices, use IntelliJ's "Code with Me" to share workspace and allow other participants to execute the Client program from different terminals.

### Application Flow and Usage Instructions
The application starts with an initial menu that allows you to:

#### 1. Sign In or Create a New Account:
- When you first run the program, you will be prompted to either Sign In using an existing account or Create a New Account.
- If there is no data in the database (i.e., during the first run), you will need to create a new user account by selecting option 2.

#### 2. Post Login Menu:
- Once logged in, you will be provided with the following options:
    - Modify your profile details, such as profile name or description.
    - Look for other users using their login username.
    - Log out of the current session and exit the program.

#### 3. Edit Profile:
- In this menu, you can:
    - View your current profile details (including the profile name, description, friends list, and blocked users).
    - Modify the Profile Name or Profile Description.

#### 4. Search for Users:
- To search for a user, use their login username. The profile name may change, but the login username remains consistent.
- Once you find a user, you will have a new set of options:
    - Add/Remove Friend.
    - Block/Unblock User.
    - Go Back to the previous menu.

#### 5. Post and News Feed Interaction:
- Create Posts: Users can make posts, which will appear in their friends' feeds.
- Interact with Posts: Users can upvote or downvote posts, comment on posts, or hide posts that they do not want to see.
- View News Feed: Displays posts made by your friends.

# Class Descriptions
This project is structured using multiple classes, each implementing an interface to ensure flexibility and modularity. Below are the descriptions of all the classes:

## 1. Client (implements ClientInterface)
The Client class represents the client-side of the application where the user interacts with the system. It will establishes a connection to the server by send requests and receive responses. It also helps close the connection gracefully.

### Key Fields:
- Socket socket: Used to establish a connection between the client and server.
- ObjectOutputStream outStream: Sends data to the server.
- ObjectInputStream inStream: Receives data from the server.

### Methods:
- sendRequest(Object request): Sends a request to the server and waits for a response.
- closeConnection(): Closes the connection between the client and server.

Example Usage:
```java

Client client = new Client("localhost", 12345);
Object response = client.sendRequest("Sample request");
System.out.println("Response: " + response);
client.closeConnection();
```

## 2. Server (implements ServerInterface)
The Server class manages incoming client connections by handle multiple client connections concurrently, processes requests such as fetching or updating user data, and ensures synchronization of data. It also stores user data in a thread-safe manner using ConcurrentHashMap.

### Key Fields:
- Implements Runnable to handle each client interaction separately.
- ConcurrentHashMap<String, User> userDatabase: Stores user data.
- boolean isRunning: Indicates if the server is running.

### Methods:
- startServer(int port): Initializes the server to listen on the specified port.
- stopServer(): Stops the server gracefully.
- handleRequest(Object request, ObjectOutputStream outStream): Handles the incoming requests from clients and returns appropriate responses.

Example Usage:
```java

public static void main(String[] args) {
    Server server = new Server();
    new Thread(server).start(); // Start the server on a separate thread
}
```
## 3. ClientHandler (implements ClientHandlerInterface)
The ClientHandler class is responsible for managing individual client sessions and routing their requests to the server. It also runs a dedicated thread for each client connected to the server.

### Key Methods:
- run(): The main loop that listens for client requests, processes them, and manages the client's session.

Example Usage:
```java

Socket clientSocket = new Socket("localhost", 12345);
Server server = new Server();
ClientHandler clientHandler = new ClientHandler(clientSocket, server);
new Thread(clientHandler).start();
```

## 4. User (implements UserInterface)
The User class represents a user in the application. It will stores user details such as login credentials, profile information and manage their interactions with other users.

### Key Fields:
- String username: The user’s unique identifier.
- ArrayList<User> friends: List of the user’s friends.
- ArrayList<User> blockedUsers: List of users blocked by this user.

### Methods:
- addFriend(User friend): Adds another user to the friend list.
- blockUser(User userToBlock): Blocks another user and removes them from the friend list.
- loginAttempt(String username, String password): Validates login credentials.

Example Usage:
```java

User user1 = new User("user1", "password1", "User One Biography");
User user2 = new User("user2", "password2", "User Two Biography");

// Adding and blocking friends
user1.addFriend(user2);
user1.blockUser(user2);
System.out.println("Blocked users: " + user1.getBlockedUsers());
```

## 5. UserDatabase (implements UserDatabaseInterface)
The UserDatabase class manages all user data, supports adding users, and provides data persistence.

### Methods:
- addUser(User user): Adds a new user to the database.
- createDatabaseFile(): Writes all user information to a file for persistence.
- saveUsersToFile(): Persists user data by saving it to a file.

Example Usage:
```java

UserDatabase userDatabase = new UserDatabase();
User newUser = new User("newUser", "password123", "User biography");

// Adding user to the database
userDatabase.addUser(newUser);
userDatabase.createDatabaseFile();
System.out.println("User saved in the database.");
```

## 6. PostClass (implements PostInterface)
The PostClass represents individual posts and manages their interactions like upvotes and downvotes.

### Key Fields:
- Manages user-created content such as postID, postDate, postTime, postTitle, postDescription, upVotes, downVotes.

### Methods:
- createPostFile(): Creates a file to store post information.
- deletePostFile(): Deletes the post's file.
- upvote(), downvote(): Increment upvotes or downvotes and allow other users to interact with posts.

Example Usage:
```java

User poster = new User("posterUser", "password", "Poster biography");
PostClass post = new PostClass(1, "2024-12-01", "10:30", poster, "First Post", "This is a post description.", 0, 0);

// Upvoting the post
post.upvote();
System.out.println("Post upvotes: " + post.getUpVotes());
```

## 7. Comments (implements CommentsInterface)
The Comments class allows users to create and manage comments on posts.

### Key Fields:
- String commentText: The content of the comment.
- int upvotes, int downvotes: Track the popularity of each comment.

### Methods:
- appendCommentToPostFile(): Adds a comment to the post.
- deleteCommentFromPostFile(User commenter): Allows the user to delete their own comment.

Example Usage:
```java

User commenter = new User("commenterUser", "password", "Commenter biography");
PostClass post = new PostClass(1, "2024-12-01", "11:00", commenter, "Sample Post", "Post description.", 0, 0);
Comments comment = new Comments("2024-12-01", "11:15", commenter, "This is a comment.", post);

comment.appendCommentToPostFile();
comment.upvote();
System.out.println("Comment upvotes: " + comment.getUpvotes());
```

## 8. NewsFeed (implements NewsFeedInterface)
The NewsFeed class manages a user's feed and displays posts from their friends.

### Methods:
- readPostsFromFile(String fileName): Reads posts from a file.
- displayFeed(): Displays all posts except hidden ones.
- hidePost(String post), disclosePost(String post): Manage hidden posts.

Example Usage:
```java

NewsFeed newsFeed = new NewsFeed();
newsFeed.addFriendPost("Friend's post about Java");
newsFeed.displayFeed();
```
# Testing

## Overview
Comprehensive testing was carried out to validate all features except direct IO-related features like multithreaded server-client interaction. Testing is split into unit tests for individual components and manual testing for IO features.

### Unit Testing (JUnit)

#### 1. UserTestCases:
- Validates that users are created correctly.
- Verifies that adding/removing friends and blocking users work properly.
- Tests user login credentials.
```java

@Test
public void testAddFriend() {
    newTestUser.addFriend(newFriendableUser);
    assertTrue(newTestUser.isFriend(newFriendableUser));
}
```

#### 2. PostClassTest:
- Ensures that posts are created and stored properly.
- Verifies that posts can be interacted with using Upvote/Downvote.

```java
@Test
void testUpvote() throws IOException {
    post.upvote();
    assertEquals(1, post.getUpVotes(), "Upvotes should be incremented by 1.");
}
```

#### 3. NewsFeedTest:
- Ensures that posts are loaded and displayed as intended.
- Verifies the ability to hide or disclose posts.

```java

@Test
void testHidePost() {
    newsFeed.hidePost("Blocked Post");
    assertTrue(newsFeed.getHiddenPosts().contains("Blocked Post"));
}
```

#### 4. CommentTest:
- Checks that comments are added to posts.
- Ensures comments can be deleted.

```java

@Test
public void testAppendAndUpvoteComment() {
    User commenter = new User("commenter", "password", "Biography");
    PostClass post = new PostClass(1, "2024-11-02", "10:00", commenter, "Test Post", "Test description.", 0, 0);

    Comments comment = new Comments("2024-11-02", "10:05", commenter, "Test comment.", post);
    comment.appendCommentToPostFile();
    comment.upvote();
    assertEquals(1, comment.getUpvotes());
}
```

### Manual Testing (Server and Client IO)
Due to the complexity of Network I/O, manual testing was conducted for:

- Multiple clients were connected simultaneously to validate thread safety and proper data handling.
- Verified that adding friends, blocking users, and creating posts by one user were properly reflected across other clients.
- Tested abrupt disconnection of clients to ensure the server’s stability.

### Running Tests
- Ensure that JUnit5 is configured in your IDE.
- Use your IDE to open each test file (UserTestCases.java, PostClassTest.java, NewsFeedTest.java, CommentTest.java) and run them with the test runner.

