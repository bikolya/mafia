package dbService;

import dbService.executor.TExecutor;
import dbService.executor.TransactionExecutor;
import dbService.handlers.TResultHandler;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    private Connection conn;

    public UserDAO(Connection conn){
        this.conn = conn;
    }

    public User get(long id) throws SQLException{
        TExecutor exec = new TExecutor();
        TResultHandler<User> r = new TResultHandler<User>(){

            public User handle(ResultSet result) throws SQLException {
                result.next();
                return new User(result.getLong(1), result.getString(2), result.getString(3));
            }
        };
        return exec.execQuery(conn, "SELECT * FROM users WHERE id=" + id, r);
    }

    public User get(String name) throws SQLException{
        TExecutor exec = new TExecutor();
        TResultHandler<User> r = new TResultHandler<User>(){

            public User handle(ResultSet result) throws SQLException {
                result.next();
                return new User(result.getLong(1), result.getString(2), result.getString(3));
            }

        };
        return exec.execQuery(conn, "SELECT * FROM users WHERE name=" + escape(name), r);
    }

    public void create(User u) throws SQLException{
        TransactionExecutor exec = new TransactionExecutor();
        exec.execUpdate(conn, "INSERT INTO users (name, password) VALUES (" + escape(u.getName()) + ","
                + escape(u.getPass()) + ");");
    }

    private String escape(String str)
    {
        return "'" + str + "'";
    }
}
