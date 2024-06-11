package util;

import java.util.Base64;
public class Utils {

    private static final String USERNAME = "admin"; // Defining authorization credentials
    private static final String PASSWORD = "password";

    // Method to encode credentials
    public static String encodeCredentials() {
        String credentials = USERNAME + ":" + PASSWORD;
        return  Base64.getEncoder().encodeToString(credentials.getBytes());
    }
}
