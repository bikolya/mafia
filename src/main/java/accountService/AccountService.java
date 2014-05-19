package accountService;

import dbService.DataService;
import dbService.UserDAO;
import dbService.User;
import messageSystem.Address;
import messageSystem.MessageSystem;
import messageSystem.Subscriber;

import java.sql.SQLException;


public class AccountService implements Runnable, Subscriber {

    private final static int DELAY = 5000;

    private final DataService dataService;
    private final MessageSystem messageSystem;
    private final Address address;

    public AccountService(DataService dataServiceInit, MessageSystem messageSystemInit)
    {
        dataService = dataServiceInit;
        messageSystem = messageSystemInit;
        address = new Address();
        messageSystem.registerService(this);
    }

    public boolean checkPassword(String login, String pass)
    {
        emulateDelay();
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
        emulateDelay();
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

    public Address getAddress() {
        return address;
    }

    public MessageSystem getMessageSystem() {
        return messageSystem;
    }

    private void emulateDelay()
    {
        try {
            Thread.sleep(DELAY);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while(true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            messageSystem.execForSubscriber(this);
        }
    }
}
