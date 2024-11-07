
import java.io.*;
import java.nio.file.*;
import java.util.*;

/**
* Handles the creation of Comments
* @author Alice Nguyen
* @version November 3, 2024
*/

public class Comments implements CommentsInterface {

    private String commentDate;
    private String commentTime;
    private User commenter;
    private String commentText;
    private PostClass post;
    private int upvotes;
    private int downvotes;

    public Comments(String commentDate, String commentTime, User commenter, String commentText, PostClass post) {
        this.commentDate = commentDate;
        this.commentTime = commentTime;
        this.commenter = commenter;
        this.commentText = commentText;
        this.post = post;
        this.upvotes = 0;
        this.downvotes = 0;
    }

    @Override
    public void appendCommentToPostFile() {
        String filename = "comments/" + post.getPostID() + ".txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            writer.write(commentDate + "\n");
            writer.write(commentTime + "\n");
            writer.write(commenter.getUsername() + "\n");
            writer.write(commentText + "\n");
            writer.write(upvotes + "\n");
            writer.write(downvotes + "\n");
            writer.write("-----\n");
            System.out.println("Comment appended to file: " + filename);
        } catch (IOException e) {
            System.err.println("Error appending comment to file: " + e.getMessage());
        }
    }

    @Override
    public void upvote() {
        upvotes++;
        appendCommentToPostFile();
    }

    @Override
    public void downvote() {
        downvotes++;
        appendCommentToPostFile();
    }

    @Override
    public void deleteCommentFromPostFile(User commenter) {
        String filename = "comments/" + post.getPostID() + ".txt";
        try {
            Path path = Paths.get(filename);
            String content = new String(Files.readAllBytes(path));
            String commentData = "Comment Date: " + commentDate + "\n" +
                    "Comment Time: " + commentTime + "\n" +
                    "Commenter: " + commenter.getUsername() + "\n" +
                    "Comment Text: " + commentText + "\n" +
                    "Upvotes: " + upvotes + "\n" +
                    "Downvotes: " + downvotes + "\n" +
                    "-----\n";

            content = content.replace(commentData, "");
            Files.write(path, content.getBytes());
            System.out.println("Comment deleted from file: " + filename);
        } catch (IOException e) {
            System.err.println("Error deleting comment from file: " + e.getMessage());
        }
    }

    @Override
    public String getCommentText() {
        return commentText;
    }

    @Override
    public User getCommenter() {
        return commenter;
    }

    @Override
    public PostClass getPost() {
        return post;
    }

    @Override
    public int getUpvotes() {
        return upvotes;
    }

    @Override
    public int getDownvotes() {
        return downvotes;
    }
}
