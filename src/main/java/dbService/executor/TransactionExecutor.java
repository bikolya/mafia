package dbService.executor;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class TransactionExecutor {

    public void execUpdate(Connection connection, String[] updates) throws SQLException
    {
        try {
            connection.setAutoCommit(false);
            for(String update: updates){
                Statement stmt = connection.createStatement();
                stmt.executeUpdate(update);
                stmt.close();
            }
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ignore) {}
            throw e;
        }
    }
    public void execUpdate(Connection connection, String update) throws SQLException
    {
        String updates[] = {update};
        execUpdate(connection, updates);
    }
}