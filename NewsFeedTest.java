import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Testing for the News Feed class
 *
 * @author Yog Trivedi
 * @version November 17, 2024
 */

class NewsFeedTest {

    private NewsFeed newsFeed;

    @BeforeEach
    void setUp() {
        newsFeed = new NewsFeed();
    }

    @Test
    void testAddFriendPost() {
        newsFeed.addFriendPost(new PostClass());
        newsFeed.addFriendPost(new PostClass());

        ArrayList<PostClass> friendPosts = newsFeed.getFriendPosts();
        assertEquals(2, friendPosts.size());
        assertTrue(friendPosts.contains("Friend's Post 1"));
        assertTrue(friendPosts.contains("Friend's Post 2"));
    }

    @Test
    void testHidePost() {
        newsFeed.hidePost(new PostClass());

        ArrayList<PostClass> hiddenPosts = newsFeed.getHiddenPosts();
        assertEquals(1, hiddenPosts.size());
        assertTrue(hiddenPosts.contains("Blocked Post"));
    }

    @Test
    void testDisclosePost() {
        newsFeed.hidePost(new PostClass());
        newsFeed.disclosePost(new PostClass());

        ArrayList<PostClass> hiddenPosts = newsFeed.getHiddenPosts();
        assertEquals(0, hiddenPosts.size());
    }

    @Test
    void testReadPostsFromFile() throws IOException {
        // Creating a temporary file with test posts
        String fileName = "testPosts.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("Post 1\n");
            writer.write("Post 2\n");
        }

        newsFeed.readPostsFromFile(fileName);

        ArrayList<PostClass> allPosts = newsFeed.getAllPosts();
        assertEquals(2, allPosts.size());
        assertTrue(allPosts.contains("Post 1"));
        assertTrue(allPosts.contains("Post 2"));

        // Clean up the test file after use
        new java.io.File(fileName).delete();
    }

    @Test
    void testDisplayFeed() {
        newsFeed.addFriendPost(new PostClass());
        newsFeed.hidePost(new PostClass());
        newsFeed.readPostsFromFile("testPosts.txt");

        ArrayList<PostClass> allPosts = newsFeed.getAllPosts();
        ArrayList<PostClass> hiddenPosts = newsFeed.getHiddenPosts();

        for (PostClass post : allPosts) {
            if (!hiddenPosts.contains(post)) {
                System.out.println(post);  // Expected to print non-hidden posts only
            }
        }
    }
}
