/**
 * Handles the creation and storage of Posts.
 *
 * @author Asher Earnhart
 * @version November 3, 2024
 */

public interface PostInterface {

    // Methods to manage file creation and deletion for the post
    void createPostFile();
    void deletePostFile();

    // Methods to increment upVotes and downVotes
    void upvote();
    void downvote();

    // Getter methods for all variables
    int getPostID();
    String getPostDate();
    String getPostTime();
    User getOriginalPoster();
    String getPostTitle();
    String getPostDescription();
    int getUpVotes();
    int getDownVotes();
}
