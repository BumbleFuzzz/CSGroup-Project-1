
import java.io.*;

public class Comments {
    int commentID;
    String date;
    String time;
    User commenter;
    String commentText;
    int upVotes;
    int downVotes;
    PostClass post;
    private static int IDIncrementer = 1; // Used to assign unique comment IDs

    // Constructor
    public Comments(String date, String time, User commenter, String commentText, PostClass post) {
        this.commentID = IDIncrementer++;
        this.date = date;
        this.time = time;
        this.commenter = commenter;
        this.commentText = commentText;
        this.upVotes = 0;
        this.downVotes = 0;
        this.post = post;
    }

    // Method to create a file for the comment
    public void createCommentFile() {
        String filename =  commentID + ".txt";
        File file = new File("comments/" + filename) ;
        try (FileWriter writer = new FileWriter(file)){
            writer.write("Comment ID: " + commentID + "\n");
            writer.write("Date: " + date + "\n");
            writer.write("Time: " + time + "\n");
            writer.write("Commenter: " + commenter.getUsername() + "\n");
            writer.write("Comment Text: " + commentText + "\n");
            writer.write("Upvotes: " + upVotes + "\n");
            writer.write("Downvotes: " + downVotes + "\n");
            System.out.println("File created: " + filename);
        } catch (IOException e) {
            System.err.println("Error creating file: " + e.getMessage());
        }
    }

    // Method to delete the comment file (can only be done by post owner or comment owner)
    public void deleteCommentFile(User requestor) {
        if (requestor.equals(commenter) || requestor.equals(post.getOriginalPoster())) {
            String filename = "comments/comment-" + commentID + ".txt";
            File file = new File(filename);
            if (file.exists()) {
                if (file.delete()) {
                    System.out.println("Comment file deleted: " + filename);
                } else {
                    System.err.println("Failed to delete comment file.");
                }
            } else {
                System.err.println("Comment file not found.");
            }
        } else {
            System.err.println("User does not have permission to delete this comment.");
        }
    }

    // Method to increment upVotes
    public void upvote() {
        this.upVotes++;
    }

    // Method to increment downVotes
    public void downvote() {
        this.downVotes++;
    }

    // Getter methods for all variables
    public int getCommentID() {
        return commentID;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public User getCommenter() {
        return commenter;
    }

    public String getCommentText() {
        return commentText;
    }

    public int getUpVotes() {
        return upVotes;
    }

    public int getDownVotes() {
        return downVotes;
    }

    public PostClass getPost() {
        return post;
    }
}
