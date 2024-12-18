import java.util.ArrayList;

/**
 * Handles the creation and manipulation of Users.
 *
 * @author Austin Napier
 * @version November 3, 2024
 */

public class User implements UserInterface {
    private String username;
    private String password;
    private String biography;
    private ArrayList<String> blockedUsers;
    private ArrayList<String> friends;
    private ArrayList<PostClass> hiddenPosts;
    private static int IDIncrementer = 1; // Used to assign unique userIDs

    public User(String username, String password, String biography) {  // Initializes a User object
        this.username = username;
        this.password = password;
        this.biography = biography;
        blockedUsers = new ArrayList<String>();
        friends = new ArrayList<String>();
        hiddenPosts = new ArrayList<PostClass>();
        IDIncrementer++; // Makes sure that the next user gets a different ID
    }

    // GETTER FUNCTIONS

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getBiography() {
        return biography;
    }

    public ArrayList<String> getBlockedUsers() {
        return blockedUsers;
    }

    public ArrayList<String> getFriends() {
        return friends;
    }

    public ArrayList<PostClass> getHiddenPosts() {
        return hiddenPosts;
    }

    // SETTER METHODS
    public void setName(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    // Misc Data Management Methods

    public void addFriend(String name) { // Adds the user passed as an argument to this User's friendlist, and vice versa
        friends.add(name);
    }

    public void removeFriend(String friend) { // Removes both users from each others friend lists
        friends.remove(friend);
    }

    public void blockUser(String userToBlock) { // Adds both users to each others block lists
        blockedUsers.add(userToBlock);
    }

    public boolean isFriend(String userToCheck) { // Checks to see if the two users are friended to each other
        return (friends.contains(userToCheck));
    }

    public boolean isBlocked(String userToCheck) {
        return (blockedUsers.contains(userToCheck));
    }

    public void hidePost(PostClass postToHide) { // Adds post to this user's list of hidden posts
        hiddenPosts.add(postToHide);
    }

    public boolean loginAttempt(String username, String password) { // Returns true if the correct username and password are passed, and false otherwise
        return (username.equals(this.username) && password.equals(this.password));
    }

    public String toString() { // Overrides toString
        String toReturn = (username + "," + password + "," + biography);
        if (!getFriends().isEmpty()) {
            toReturn+= ",";
            for (String friend : getFriends()) {
                toReturn += "&" + friend;
            }
        }
        if (!blockedUsers.isEmpty()) {
            toReturn+= ",";
            for (String blocked : blockedUsers) {
                toReturn += "*" + blocked;
            }
        }

        return toReturn;
    }

    public boolean equals(Object o) { // Overrides equals
        if (!(o instanceof User)) {
            return false;
        }
        return (this.username == ((User) o).username);
    }
}
