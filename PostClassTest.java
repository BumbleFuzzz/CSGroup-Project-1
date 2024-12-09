import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test cases for the Post Class
 *
 * @author Asher Earnhart
 * @version November 17, 2024
 */

public class PostClassTest {

    private PostClass post;
    private final String filename = "posts/post-1.txt";

    @BeforeEach
    void setUp() {
        // Assuming User class has a constructor User(String username)
        User user = new User("testUser","hi","no boi");
        post = new PostClass(user, "Test Post", "This is a test post.", 0, 0);
    }

    @AfterEach
    void tearDown() {
        // Ensure the post file is deleted after each test
        File file = new File(filename);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void testCreatePostFile() {
        post.createPostFile();
        assertTrue(Files.exists(Paths.get(filename)), "Post file should be created.");
    }

    @Test
    void testDeletePostFile() {
        post.createPostFile();
        post.deletePostFile();
        assertFalse(Files.exists(Paths.get(filename)), "Post file should be deleted.");
    }

    @Test
    void testUpvote() throws IOException {
        post.createPostFile();
        int initialUpvotes = post.getUpVotes();
        post.upvote();
        assertEquals(initialUpvotes + 1, post.getUpVotes(), "Upvotes should be incremented by 1.");

        // Verify file was updated
        String content = Files.readString(Paths.get(filename));
        assertTrue(content.contains(String.valueOf(initialUpvotes + 1)), "File should contain updated upvotes.");
    }

    @Test
    void testDownvote() throws IOException {
        post.createPostFile();
        int initialDownvotes = post.getDownVotes();
        post.downvote();
        assertEquals(initialDownvotes + 1, post.getDownVotes(), "Downvotes should be incremented by 1.");

        // Verify file was updated
        String content = Files.readString(Paths.get(filename));
        assertTrue(content.contains(String.valueOf(initialDownvotes + 1)), "File should contain updated downvotes.");
    }

    @Test
    void testGetters() {
        assertEquals(1, post.getPostID());
        assertEquals("testUser", post.getOriginalPoster().getUsername());
        assertEquals("Test Post", post.getPostTitle());
        assertEquals("This is a test post.", post.getPostDescription());
        assertEquals(0, post.getUpVotes());
        assertEquals(0, post.getDownVotes());
    }
}
