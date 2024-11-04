
import java.io.*;
import java.nio.file.*;
import java.util.*;

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
    
    public void appendCommentToPostFile() {
        String filename = "comments/" + post.getPostID() + ".txt"; // Unique file per post
        File file = new File(filename);
        try (FileWriter writer = new FileWriter(file, true)) { // Set append mode = true
            writer.write("Comment ID: " + commentID + "\n");
            writer.write("Date: " + date + "\n");
            writer.write("Time: " + time + "\n");
            writer.write("Commenter: " + commenter.getUsername() + "\n");
            writer.write("Comment Text: " + commentText + "\n");
            writer.write("Upvotes: " + upVotes + "\n");
            writer.write("Downvotes: " + downVotes + "\n");
            writer.write("----------------------------------------------------\n"); 
            System.out.println("Comment appended to file: " + filename);
        } catch (IOException e) {
            System.err.println("Error appending to file: " + e.getMessage());
        }
    }

    // Delete a comment from the post file (can only be done by the post owner or comment owner)
    public void deleteCommentFromPostFile(User requestor) {
        if (requestor.equals(commenter) || requestor.equals(post.getOriginalPoster())) {
            String filename = "comments/" + post.getPostID() + ".txt";
            File file = new File(filename);
            List<String> lines = new ArrayList<>();
            boolean commentFound = false;

            // Read through the file and store lines in a list, excluding the comment to be deleted
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    // Check if the line contains the comment ID
                    if (line.startsWith("Comment ID: " + commentID)) {
                        commentFound = true;
                        // Skip all lines belonging to this comment
                        for (int i = 0; i < 7; i++) { // Skip the next 7 lines related to this comment
                            reader.readLine();
                        }
                    } else {
                        lines.add(line); // Add the line as-is if not part of the target comment
                    }
                }
            } catch (IOException e) {
                System.err.println("Error reading file: " + e.getMessage());
                return;
            }

            // If the comment was found, rewrite the file without the deleted comment
            if (commentFound) {
                try {
                    Files.write(file.toPath(), lines);
                    System.out.println("Comment deleted from file: " + filename);
                } catch (IOException e) {
                    System.err.println("Error writing file: " + e.getMessage());
                }
            } else {
                System.err.println("Comment with ID " + commentID + " not found in file.");
            }
        } else {
            System.err.println("User does not have permission to delete this comment.");
        }
    }

    public void updateCommentVotesInFile() {
        String filename = "comments/" + post.getPostID() + ".txt";
        File file = new File(filename);
        List<String> lines = new ArrayList<>();
        boolean commentFound = false;
    
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Check if the line contains the comment ID
                if (line.startsWith("Comment ID: " + commentID)) {
                    commentFound = true;
                    lines.add(line); // Add "Comment ID" line
                    lines.add(reader.readLine()); // Add "Date" line
                    lines.add(reader.readLine()); // Add "Time" line
                    lines.add(reader.readLine()); // Add "Commenter" line
                    lines.add(reader.readLine()); // Add "Comment Text" line
                    lines.add("Upvotes: " + upVotes); // Update "Upvotes" line
                    reader.readLine(); // Skip the old "Upvotes" line
                    lines.add("Downvotes: " + downVotes); // Update "Downvotes" line
                    reader.readLine(); // Skip the old "Downvotes" line
                    lines.add(reader.readLine()); // Add separator line
                } else {
                    lines.add(line); // Add the line as-is if not part of the target comment
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            return;
        }
    
        if (commentFound) {
            try {
                Files.write(file.toPath(), lines);
                System.out.println("Comment votes updated successfully in file: " + filename);
            } catch (IOException e) {
                System.err.println("Error writing file: " + e.getMessage());
            }
        } else {
            System.err.println("Comment with ID " + commentID + " not found in file.");
        }
    }
    

    // Method to increment upVotes and update the file
    public void upvote() {
        this.upVotes++;
        updateCommentVotesInFile();
    }

    // Method to increment downVotes and update the file
    public void downvote() {
        this.downVotes++;
        updateCommentVotesInFile();
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
