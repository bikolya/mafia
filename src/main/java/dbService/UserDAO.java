package dbService;

import dbService.executor.TExecutor;
import dbService.executor.TransactionExecutor;
import dbService.handlers.TResultHandler;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    private DataService dataService;

    public UserDAO(DataService dataServiceInit)
    {
        dataService = dataServiceInit;
    }

    public User get(String name) throws SQLException
    {
        TExecutor exec = new TExecutor();
        TResultHandler<User> resultHandler = new TResultHandler<User>()
        {
            public User handle(ResultSet result) throws SQLException
            {
                result.next();
                return new User(result.getLong(1), result.getString(2), result.getString(3));
            }
        };
        return exec.execQuery(dataService.getConnection(), "SELECT * FROM users WHERE name="
                                  + escape(name), resultHandler);
    }

    public void create(User user) throws SQLException
    {
        TransactionExecutor exec = new TransactionExecutor();
        exec.execUpdate(dataService.getConnection(), "INSERT INTO users (name, password) VALUES ("
                            + escape(user.getName()) + "," + escape(user.getPass()) + ");");
    }

    private String escape(String str)
    {
        return "'" + str + "'";
    }
}
