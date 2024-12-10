import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Used specifically to test the User and UserDatabase classes.
 *
 * @author Austin Napier
 * @version November 3, 2024
 */

public class UserTestCases {

    String username = "TestName1";
    String password = "TestPassword1";
    String biography = "TestBiography1";

    User newTestUser  = new User(username, password, biography);

    String username2 = "TestName2";
    String password2 = "TestPassword2";
    String biography2 = "TestBiography2";

    User newFriendableUser = new User(username2, password2, biography2);

    @Test
    public void testStringRepresentation() {
        System.out.println("The first new user's string representation is: ");
        System.out.println(newTestUser);
        System.out.println("The second new user's string representation is: ");
        System.out.println(newFriendableUser);
        assertNotEquals(newTestUser.toString(), newFriendableUser.toString());
    }

    @Test
    public void testAddFriend() {
        newTestUser.addFriend(newFriendableUser.getUsername());
        System.out.println(newTestUser);
        assertTrue(newTestUser.isFriend(newFriendableUser.getUsername()));
        assertTrue(newFriendableUser.isFriend(newTestUser.getUsername()));
    }

    @Test
    public void testBlock() {
        System.out.println("The first user will now block the second, also removing them from their friends list.");

        newTestUser.blockUser(newFriendableUser.getUsername());

        System.out.println(newTestUser);
        assertFalse(newTestUser.isFriend(newFriendableUser.getUsername()));
        assertTrue(newTestUser.isBlocked(newFriendableUser.getUsername()));
    }

    @Test
    public void testFileCreation() throws IOException {
        System.out.println("We will now load both of these users into the UserDatabase.");

        System.out.println("Database being created...");

        UserDatabase newDB = new UserDatabase();

        newDB.addUser(newTestUser);
        newDB.addUser(newFriendableUser);

        newDB.createDatabaseFile();

        System.out.println("UserDatabase.txt has been created.");
        assertNotNull(newDB.getCurrentDBFile());
        assertTrue(newDB.getCurrentDBFile().exists());
    }
}
