import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class PostClass {

    int postID;
    String postDate;
    String postTime;
    User originalPoster;
    String postTitle;
    String postDescription;
    int upVotes;
    int downVotes;

    // Constructor
    public PostClass(int postID, String postDate, String postTime, User originalPoster, String postTitle, String postDescription, int upVotes, int downVotes) {
        this.postID = postID;
        this.postDate = postDate;
        this.postTime = postTime;
        this.originalPoster = originalPoster;
        this.postTitle = postTitle;
        this.postDescription = postDescription;
        this.upVotes = upVotes;
        this.downVotes = downVotes;
    }

    // Method to create a file for the post
    public void createPostFile() {
        String filename = "posts/post-" + postID + ".txt";
        File file = new File(filename);
        try (FileWriter writer = new FileWriter(file)) {
            writer.write("Post ID: " + postID + "\n");
            writer.write("Date: " + postDate + "\n");
            writer.write("Time: " + postTime + "\n");
            writer.write("Poster: " + originalPoster.getUsername() + "\n");
            writer.write("Title: " + postTitle + "\n");
            writer.write("Description: " + postDescription + "\n");
            writer.write("Upvotes: " + upVotes + "\n");
            writer.write("Downvotes: " + downVotes + "\n");
            System.out.println("File created/updated: " + filename);
        } catch (IOException e) {
            System.err.println("Error creating file: " + e.getMessage());
        }

    }

    // Method to delete the file
    public void deletePostFile() {
        String filename = "posts/post-" + postID + ".txt";
        File file = new File(filename);
        if (file.exists()) {
            if (file.delete()) {
                System.out.println("File deleted: " + filename);
            } else {
                System.err.println("Failed to delete file.");
            }
        } else {
            System.err.println("File not found.");
        }
    }

    // Method to increment upVotes
    public void upvote() {
        this.upVotes++;
        createPostFile();
    }

    // Method to increment downVotes
    public void downvote() {
        this.downVotes++;
        createPostFile();
    }

    // Getter methods for all variables
    public int getPostID() {
        return postID;
    }

    public String getPostDate() {
        return postDate;
    }

    public String getPostTime() {
        return postTime;
    }

    public User getOriginalPoster() {
        return originalPoster;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public String getPostDescription() {
        return postDescription;
    }

    public int getUpVotes() {
        return upVotes;
    }

    public int getDownVotes() {
        return downVotes;
    }
}