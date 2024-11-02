import java.util.ArrayList;

public interface UserInterface {
    public String getUsername();
    public int getUserID();
    public String getPassword();
    public String getBiography();
    public ArrayList<User> getBlockedUsers();
    public ArrayList<User> getFriends();
    // public ArrayList<Post> getHiddenPosts();  UNCOMMENT LATER
    public void setName(String name);
    public void setUserID(int userID);
    public void setPassword(String password);
    public void setBiography(String biography);
    public void addFriend(User friend);
    public void removeFriend(User friend);
    public void blockUser(User userToBlock);
    public void unblockUser(User userToUnblock);
    // public void hidePost(Post postToHide);      UNCOMMENT LATER
    public boolean loginAttempt(String username, String password);
}
