import java.io.IOException;

/**
 * Interface corresponding to the Comments class.
 *
 *
 * @author Asher Earnhart
 * @version November 7, 2024
 */

public interface CommentsInterface {

    // Appends the comment to the associated post's file
    void appendCommentToPostFile() throws IOException;

    // Increments the comment's upvote count
    void upvote();

    // Increments the comment's downvote count
    void downvote();

    // Deletes the comment from the associated post's file
    void deleteCommentFromPostFile(int id) throws IOException;

    // Getter for the comment's text
    String getCommentText();

    // Getter for the comment's author
    String getCommenter();

    // Getter for the comment's associated post
    int getPost();

    // Getter for the comment's upvote count
    int getUpvotes();

    // Getter for the comment's downvote count
    int getDownvotes();
}
