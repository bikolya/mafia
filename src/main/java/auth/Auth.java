package auth;

import java.util.HashMap;
import java.util.Map;

public class Auth {

    private static Map<String, String> users = new HashMap<>();

    public static void backDoor()
    {
        registerUser("1", "1");
        registerUser("2", "2");
    }

    public static boolean checkAuth(String login, String pass)
    {
        return users.get(login).equals(pass);
    }

    public static boolean checkUser(String login)
    {
        return users.containsKey(login);
    }

    public static boolean registerUser(String login, String pass)
    {
        if(!checkUser(login)) {
            users.put(login, pass);
            return true;
        } else {
            return false;
        }
    }
}
