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
    public ArrayList<User> getBlockedUsers();
    public ArrayList<User> getFriends();
    public ArrayList<PostClass> getHiddenPosts();
    public void setName(String name);
    public void setPassword(String password);
    public void setBiography(String biography);
    public void addFriend(User friend);
    public void removeFriend(User friend);
    public void blockUser(User userToBlock);
    public void unblockUser(User userToUnblock);
    public void hidePost(PostClass postToHide);
    public boolean loginAttempt(String username, String password);
}
