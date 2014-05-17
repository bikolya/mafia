package dbService;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataService {
    private Connection conn = null;

    public Connection getConnection()
    {
        if (conn != null) {
            return conn;
        } else
            try {
                DriverManager.registerDriver((Driver) Class.forName("com.mysql.jdbc.Driver").newInstance());

                StringBuilder url = new StringBuilder();
                url.
                        append("jdbc:mysql://").
                        append("localhost:").
                        append("3306/").
                        append("mafia?").
                        append("user=bikolya&").
                        append("password=qweqwe");

                return DriverManager.getConnection(url.toString());
            } catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                e.printStackTrace();
        }
        return null;
    }
}
