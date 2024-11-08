import java.io.*;
import java.util.ArrayList;

public class NewsFeed implements NewsFeedInterface
{
    private ArrayList<String> allPosts;
    // All posts in the feed

    private ArrayList<String> friendPosts;
    // Posts from specifically friends

    private ArrayList<String> hiddenPosts;
    // Posts from blocked users

    public NewsFeed()
    {
        allPosts = new ArrayList<>();
        friendPosts = new ArrayList<>();
        hiddenPosts = new ArrayList<>();
    }

    // Reads posts from a given file and displays them to the feed
    public void readPostsFromFile(String fileName)
    {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName)))
        {
            String post;
            while ((post = br.readLine()) != null)
            {
                allPosts.add(post);
            }
            displayFeed();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    // Shows all posts in the feed
    public void displayFeed()
    {
        System.out.println("Here is your News Feed!");
        for (String post : allPosts)
        {
            if (!hiddenPosts.contains(post))
            {
                System.out.println(post);
            }
        }
    }

    // Adds a post to friendPosts
    public void addFriendPost(String post)
    {
        if (!friendPosts.contains(post))
        {
            friendPosts.add(post);
        }
    }

    // Hides a post
    public void hidePost(String post)
    {
        if (!hiddenPosts.contains(post))
        {
            hiddenPosts.add(post);
        }
    }

    // Makes a post observable after being hidden
    public void disclosePost(String post)
    {
        hiddenPosts.remove(post);
    }

    // Getters for each respective type of post

    public ArrayList<String> getAllPosts()
    {
        return allPosts;
    }

    public ArrayList<String> getFriendPosts()
    {
        return friendPosts;
    }

    public ArrayList<String> getHiddenPosts()
    {
        return hiddenPosts;
    }
}
