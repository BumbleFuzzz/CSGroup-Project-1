import java.util.ArrayList;

/**
 * Interface for the News Feed class
 *
 * @author Yog Trivedi
 * @version November 17, 2024
 */

public interface NewsFeedInterface
{


    void displayFeed();

    void addFriendPost(PostClass post);

    void hidePost(PostClass post);

    void disclosePost(PostClass post);

    ArrayList<PostClass> getAllPosts();

    ArrayList<PostClass> getFriendPosts();

    ArrayList<PostClass> getHiddenPosts();

    void clearFeed();

}
