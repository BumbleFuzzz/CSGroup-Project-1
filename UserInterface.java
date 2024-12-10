import java.util.ArrayList;

/**
 * Interface corresponding to the User class
 *
 * @author Austin Napier
 * @version November 3, 2024
 */

public interface UserInterface {
    public String getUsername();
    public String getPassword();
    public String getBiography();
    public ArrayList<String> getBlockedUsers();
    public ArrayList<String> getFriends();
    public ArrayList<PostClass> getHiddenPosts();
    public void setName(String name);
    public void setPassword(String password);
    public void setBiography(String biography);
    public void addFriend(String name);
    public void removeFriend(String friend);
    public void blockUser(String userToBlock);
    public void hidePost(PostClass postToHide);
    public boolean loginAttempt(String username, String password);
}
