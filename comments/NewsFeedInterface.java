import java.util.ArrayList;

public interface NewsFeedInterface
{

    void readPostsFromFile(String filename);

    void displayFeed();

    void addFriendPost(String post);

    void hidePost(String post);

    void disclosePost(String post);

    ArrayList<String> getAllPosts();

    ArrayList<String> getFriendPosts();

    ArrayList<String> getHiddenPosts();

}
