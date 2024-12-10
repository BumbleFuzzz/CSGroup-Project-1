
import java.io.*;
import java.nio.file.*;
import java.util.*;

/**
* Handles the creation of Comments
* @author Alice Nguyen
* @version November 3, 2024
*/

public class Comments implements CommentsInterface {

    private int postID;
    private int upvotes;
    private int downvotes;
    private String commenter;
    private String commentText;
    private int commentID;
    static int commentIDIncrementor = 1;

    public Comments(int postID, String commenter, String commentText) {
        commentID = commentIDIncrementor;
        commentIDIncrementor++;
        this.commenter = commenter;
        this.commentText = commentText;
        this.upvotes = 0;
        this.downvotes = 0;
        this.postID = postID;
    }

    @Override
    public void appendCommentToPostFile() {
        String filename = "comments/" + postID + ".txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            writer.write(commentID+ "," + commenter + "," + commentText + "," + upvotes + "," + downvotes + "\n");
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
    public void deleteCommentFromPostFile(int id) {
        String filename = "comments/" + postID + ".txt";
        try {
            Path path = Paths.get(filename);
            String content = new String(Files.readAllBytes(path));
            String commentData = (commentID + "," + commenter + "," + commentText + "," + upvotes + "," + downvotes);

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
    public String getCommenter() {
        return commenter;
    }

    @Override
    public int getPost() {
        return postID;
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
