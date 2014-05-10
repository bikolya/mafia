package auth;

import dbService.DataService;
import dbService.UserDAO;
import dbService.User;

import java.sql.SQLException;

public class AccountService {

    private DataService dataService;

    public AccountService(DataService dataServiceInit)
    {
        dataService = dataServiceInit;
    }

    public boolean checkPassword(String login, String pass)
    {
        UserDAO userDAO = new UserDAO(dataService);
        try {
            User user = userDAO.get(login);
            return user.getPass().equals(pass);
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        }
    }

    public boolean registerUser(String login, String pass)
    {
        if ( login.isEmpty() || pass.isEmpty() )
            return false;

        UserDAO userDAO = new UserDAO(dataService);

        try {
            userDAO.create(new User (login, pass));
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        }
        return true;
    }

    public long getUserId(String login)
    {
        UserDAO userDAO = new UserDAO(dataService);
        long id = -1;
        try {
            id = userDAO.get(login).getId();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }
}
