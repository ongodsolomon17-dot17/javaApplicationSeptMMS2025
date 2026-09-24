package hospital.security;

import hospital.models.User;

public class Session {

    private static User currentUser;

    private Session() {
        // Prevent creating Session objects
    }

    public static void start(User user) {
        currentUser = user;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static boolean isLoggedIn() {
        return currentUser != null;
    }

    public static void end() {
        currentUser = null;
    }

    public static String getUsername() {
        if (currentUser == null) {
            return null;
        }

        return currentUser.getUsername();
    }

    public static String getRole() {
        if (currentUser == null) {
            return null;
        }

        return currentUser.getRole();
    }
}