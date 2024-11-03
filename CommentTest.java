import java.io.*;
import java.nio.file.*;

public class CommentTest {
    public static void main(String[] args) {
        // Set up test data
        User poster = new User("poster", "password123", "Original poster biography");
        PostClass post = new PostClass(1, "2024-11-02", "10:00", poster, "Test Post Title", "This is a test post description.", 0, 0);
    
        // Create a commenter
        User commenter = new User("commenter", "password456", "Commenter biography");
        Comments comment = new Comments("2024-11-02", "10:05", commenter, "This is a test comment.", post);

        
        comment.appendCommentToPostFile();

        for(int i = 0; i < 5; i++)
            comment.upvote();
        comment.downvote();

        printFileContents("comments/1.txt");
    }

    public static void printFileContents(String filename) {
        System.out.println("Reading contents of file: " + filename);
        File file = new File(filename);
        
        if (!file.exists()) {
            System.out.println("File does not exist: " + filename);
            return;
        }
    
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }    

}
