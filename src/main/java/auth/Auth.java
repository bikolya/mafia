package auth;

import dbService.DataService;
import dbService.UserDAO;
import dbService.User;

import java.sql.SQLException;
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
        UserDAO ud = new UserDAO(DataService.conn);
        try {
            User u = ud.get(login);
            return u.getPass().equals(pass);
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        }
    }

    public static boolean checkUser(String login)
    {
        return users.containsKey(login);
    }

    public static boolean registerUser(String login, String pass)
    {
        if ( (login.length() == 0) || (pass.length() == 0) )
            return false;

        User u = new User (login, pass);
        UserDAO ud = new UserDAO(DataService.conn);

        try {
            ud.create(u);
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        }
        return true;
    }
}
