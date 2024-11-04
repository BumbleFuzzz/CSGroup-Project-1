import java.util.ArrayList;

/**
 * Handles the creation and manipulation of Users.
 *
 * @author Austin Napier
 * @version November 3, 2024
 */

public class User implements UserInterface {
    private String username;
    private int userID;
    private String password;
    private String biography;
    private ArrayList<User> blockedUsers;
    private ArrayList<User> friends;
    private ArrayList<PostClass> hiddenPosts;
    private static int IDIncrementer = 1; // Used to assign unique userIDs

    public User(String username, String password, String biography) {  // Initializes a User object
        this.username = username;
        this.password = password;
        this.biography = biography;
        blockedUsers = new ArrayList<User>();
        friends = new ArrayList<User>();
        hiddenPosts = new ArrayList<PostClass>();
        userID = IDIncrementer;
        IDIncrementer++; // Makes sure that the next user gets a different ID
    }

    // GETTER FUNCTIONS

    public String getUsername() {
        return username;
    }

    public int getUserID() {
        return userID;
    }

    public String getPassword() {
        return password;
    }

    public String getBiography() {
        return biography;
    }

    public ArrayList<User> getBlockedUsers() {
        return blockedUsers;
    }

    public ArrayList<User> getFriends() {
        return friends;
    }

    public ArrayList<PostClass> getHiddenPosts() {
        return hiddenPosts;
    }

    // SETTER METHODS
    public void setName(String username) {
        this.username = username;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    // Misc Data Management Methods

    public void addFriend(User friend) { // Adds the user passed as an argument to this User's friendlist, and vice versa
        if (!friends.contains(friend) && !blockedUsers.contains(friend)) {
            friends.add(friend);
            friend.friends.add(this);
        }
    }

    public void removeFriend(User friend) { // Removes both users from each others friend lists
        if (friends.contains(friend)) {
            friends.remove(friend);
            friend.friends.remove(this);
        }
    }

    public void blockUser(User userToBlock) { // Adds both users to each others block lists
        if (!blockedUsers.contains(userToBlock)) {
            if (friends.contains(userToBlock)) {
                friends.remove(userToBlock);
                userToBlock.friends.remove(this);
            }
            blockedUsers.add(userToBlock);
            userToBlock.blockedUsers.add(this);
        }
    }

    public void unblockUser(User userToUnblock) { // Removes both users from each others block lists
        if (blockedUsers.contains(userToUnblock)) {
            blockedUsers.remove(userToUnblock);
            userToUnblock.blockedUsers.remove(this);
        }
    }

    public boolean isFriend(User userToCheck) { // Checks to see if the two users are friended to each other
        return (friends.contains(userToCheck));
    }

    public void hidePost(PostClass postToHide) { // Adds post to this user's list of hidden posts
        hiddenPosts.add(postToHide);
    }

    public boolean loginAttempt(String username, String password) { // Returns true if the correct username and password are passed, and false otherwise
        return (username.equals(this.username) && password.equals(this.password));
    }

    public String toString() { // Overrides toString
        String toReturn = ("Name: " + username + "\nID: " + userID + "\nPassword: " + password + "\nBiography: " + biography + "\nFriends: ");
        if (!friends.isEmpty()) {
            for (User friend : friends) {
                toReturn += friend.username + " - ID: " + friend.userID + "\n";
            }
        } else {
            toReturn += "No friended users found!\n";
        }

        return toReturn;
    }

    public boolean equals(Object o) { // Overrides equals
        if (!(o instanceof User)) {
            return false;
        }
        return (this.userID == ((User) o).userID);
    }
}
