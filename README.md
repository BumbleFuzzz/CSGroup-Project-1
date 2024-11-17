# CSGroup-Project-1
## Overview

This project includes features such as user registration, posting content, managing a news feed, comments, and server-client communication. The project consists of multiple components such as a client-server model, user management, posts, comments, and a news feed. 

## Brightspace and Vocareum Submisson
Asher Earnhart submitted Phase 1 workspace to Vocareum

## Instructions for Compilation and Execution
Prerequisites:
Java Development Kit (JDK) installed.
A compatible IDE such as IntelliJ IDEA or Eclipse.
JUnit5 for testing purposes.

### Steps:
- Clone or download the project repository.
- Open the project in your preferred IDE.
- Compile the code using the IDE's build tools or javac.
- Run the application components:
    - Server: Start by running the Server class, which listens on port 12345.
    - Client: Connect the client to the server by running the Client class.
- Run the provided test cases to validate individual components.

# Class Descriptions

## 1. Client
The Client class handles the client-side operations and communication with the server.

### Purpose:
- Establish a connection to the server.
- Send requests and receive responses.
- Close the connection gracefully.

### Key Fields:
- Socket socket: Establishes a connection to the server.
- ObjectOutputStream outStream: Sends data to the server.
- ObjectInputStream inStream: Receives data from the server.

### Methods:
- sendRequest(Object request): Sends a request to the server and waits for a response.
- closeConnection(): Closes the connection with the server.

Example Usage:
```java

Client client = new Client("localhost", 12345);
Object response = client.sendRequest("Sample request");
System.out.println("Response: " + response);
client.closeConnection();
```

## 2. Server
The Server class manages client connections and processes their requests. It also stores user data in a thread-safe manner using ConcurrentHashMap.

### Purpose:
- Handle multiple client connections concurrently.
- Process requests such as fetching or updating user data.

### Key Fields:
- ConcurrentHashMap<String, User> userDatabase: Stores user data.
- boolean isRunning: Indicates if the server is running.

### Methods:
- startServer(int port): Initializes the server to listen on the specified port.
- stopServer(): Stops the server gracefully.
- handleRequest(Object request, ObjectOutputStream outStream): Processes requests from clients.

Example Workflow:
```java
Server server = new Server();
server.startServer(12345);
```
## 3. ClientHandler
ClientHandler is responsible for managing individual client sessions and routing their requests to the server.

### Purpose:
- Enable bi-directional communication between the client and server.
- Process requests and send appropriate responses.

### Key Methods:
- run(): Starts a session for the connected client.


## 4. User
The User class represents a user in the application.

### Purpose:
- Store user details and manage their interactions with other users.

### Fields:
- String username: The user’s unique identifier.
- ArrayList<User> friends: List of the user’s friends.
- ArrayList<User> blockedUsers: List of users blocked by this user.

### Methods:
- addFriend(User friend): Adds another user to the friend list.
- blockUser(User userToBlock): Blocks another user and removes them from the friend list.
- loginAttempt(String username, String password): Validates login credentials.

## 5. UserDatabase
The UserDatabase class manages a collection of User objects.

### Purpose:
- Provide persistent storage for user information.

### Methods:
- addUser(User user): Adds a user to the database.
- createDatabaseFile(): Writes all user information to a file for persistence.


## 6. PostClass
Represents individual posts in the application.

### Fields:
- postID, postDate, postTime, postTitle, postDescription, upVotes, downVotes.

### Key Methods:
- createPostFile(): Creates a file to store post information.
- deletePostFile(): Deletes the post's file.
- upvote, downvote: Increment upvotes or downvotes.

## 7. Comments
The Comments class handles comments on posts.

### Fields:
- String commentText: The content of the comment.
- int upvotes, int downvotes: Tracks engagement with the comment.

### Methods:
- appendCommentToPostFile(): Adds the comment to the respective post’s file.
- deleteCommentFromPostFile(User commenter): Deletes the comment from the file.


## 8. NewsFeed
Manages and displays posts in a user’s feed.

### Key Methods:
- readPostsFromFile(String fileName): Reads posts from a file.
- displayFeed(): Displays all posts except hidden ones.
- hidePost(String post), disclosePost(String post): Manage hidden posts.

## Testing
Unit tests are provided for key classes. To run the tests:

Open the test files in your IDE.
Ensure JUnit5 is installed.
Execute the test scripts.

