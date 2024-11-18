import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NewsFeedTest {

    private NewsFeed newsFeed;

    @BeforeEach
    void setUp() {
        newsFeed = new NewsFeed();
    }

    @Test
    void testAddFriendPost() {
        newsFeed.addFriendPost("Friend's Post 1");
        newsFeed.addFriendPost("Friend's Post 2");

        ArrayList<String> friendPosts = newsFeed.getFriendPosts();
        assertEquals(2, friendPosts.size());
        assertTrue(friendPosts.contains("Friend's Post 1"));
        assertTrue(friendPosts.contains("Friend's Post 2"));
    }

    @Test
    void testHidePost() {
        newsFeed.hidePost("Blocked Post");

        ArrayList<String> hiddenPosts = newsFeed.getHiddenPosts();
        assertEquals(1, hiddenPosts.size());
        assertTrue(hiddenPosts.contains("Blocked Post"));
    }

    @Test
    void testDisclosePost() {
        newsFeed.hidePost("Blocked Post");
        newsFeed.disclosePost("Blocked Post");

        ArrayList<String> hiddenPosts = newsFeed.getHiddenPosts();
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

        ArrayList<String> allPosts = newsFeed.getAllPosts();
        assertEquals(2, allPosts.size());
        assertTrue(allPosts.contains("Post 1"));
        assertTrue(allPosts.contains("Post 2"));

        // Clean up the test file after use
        new java.io.File(fileName).delete();
    }

    @Test
    void testDisplayFeed() {
        newsFeed.addFriendPost("Friend's Post 1");
        newsFeed.hidePost("Blocked Post");
        newsFeed.readPostsFromFile("testPosts.txt");

        ArrayList<String> allPosts = newsFeed.getAllPosts();
        ArrayList<String> hiddenPosts = newsFeed.getHiddenPosts();

        for (String post : allPosts) {
            if (!hiddenPosts.contains(post)) {
                System.out.println(post);  // Expected to print non-hidden posts only
            }
        }
    }
}
