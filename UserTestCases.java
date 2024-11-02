import java.util.*;

public class UserTestCases {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Create a new user.");

        System.out.println("Input username: ");
        String username = sc.nextLine();
        System.out.println("Input password: ");
        String password = sc.nextLine();
        System.out.println("Input biography: ");
        String biography = sc.nextLine();

        User newTestUser = new User(username, password, biography);

        System.out.println("This new user's string representation is: ");
        System.out.println(newTestUser);

        System.out.println("Now create a new user who will friend the other user.");

        System.out.println("Input username: ");
        username = sc.nextLine();
        System.out.println("Input password: ");
        password = sc.nextLine();
        System.out.println("Input biography: ");
        biography = sc.nextLine();

        User newFriendableUser = new User(username, password, biography);

        newTestUser.addFriend(newFriendableUser);

        System.out.println(newTestUser);

        System.out.println("The first user will now block the second, also removing them from their friends list.");

        newTestUser.blockUser(newFriendableUser);

        System.out.println(newTestUser);

        System.out.println("We will now load both of these users into the UserDatabase.");

        System.out.println("Database being created...");

        UserDatabase newDB = new UserDatabase();

        newDB.addUser(newTestUser);
        newDB.addUser(newFriendableUser);

        newDB.createDatabaseFile();

        System.out.println("UserDatabase.txt has been created.");
    }
}