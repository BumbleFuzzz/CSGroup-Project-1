import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Handles the creation of the news feed
 *
 * @author Yog Trivedi
 * @version November 17, 2024
 */

public class NewsFeed implements NewsFeedInterface {
    private ArrayList<PostClass> allPosts;
    // All posts in the feed

    private ArrayList<PostClass> friendPosts;
    // Posts from specifically friends

    private ArrayList<PostClass> hiddenPosts;
    // Posts from blocked users

    public NewsFeed() {
        allPosts = new ArrayList<>();
        friendPosts = new ArrayList<>();
        hiddenPosts = new ArrayList<>();
    }


    // Shows all posts in the feed
    public void displayFeed() {
        System.out.println("Here is your News Feed!");
        for (PostClass post : allPosts) {
            if (!hiddenPosts.contains(post)) {
                System.out.println(post);
            }
        }
    }

    // Adds a post to friendPosts
    public void addFriendPost(PostClass post) {
        if (!friendPosts.contains(post)) {
            friendPosts.add(post);
        }
    }

    // Hides a post
    public void hidePost(PostClass post) {
        if (!hiddenPosts.contains(post)) {
            hiddenPosts.add(post);
        }
    }

    // Makes a post observable after being hidden
    public void disclosePost(PostClass post) {
        hiddenPosts.remove(post);
    }

    // Getters for each respective type of post

    public ArrayList<PostClass> getAllPosts() {
        return allPosts;
    }

    public ArrayList<PostClass> getFriendPosts() {
        return friendPosts;
    }

    public ArrayList<PostClass> getHiddenPosts() {
        return hiddenPosts;
    }

    public void addPost(PostClass postToAdd) {
        allPosts.add(postToAdd);
    }

    public void removePost(PostClass postToRemove) {
        allPosts.remove(postToRemove);
    }

    public void clearFeed() {allPosts.clear();}
}
