import java.util.ArrayList;

/**
 * Interface for the News Feed class
 *
 * @author Yog Trivedi
 * @version November 17, 2024
 */

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
