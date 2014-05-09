package dbService;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataService {
    public static Connection conn = null;

    public static Connection getConnection()
    {
        if (conn != null) {
            return conn;
        } else
            try {
                DriverManager.registerDriver((Driver) Class.forName("com.mysql.jdbc.Driver").newInstance());

                StringBuilder url = new StringBuilder();
                url.
                        append("jdbc:mysql://").		//db type
                        append("localhost:"). 			//host name
                        append("3306/").				//port
                        append("mafia?").			//db name
                        append("user=bikolya&").	    //login
                        append("password=qweqwe");		//password

                System.out.append("URL: " + url + "\n");

                return DriverManager.getConnection(url.toString());
            } catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                e.printStackTrace();
        }
        return null;
    }

    public static void connect()
    {
        conn = getConnection();
        System.out.append("Connected!\n");
        try {
            System.out.append("Autocommit: " + conn.getAutoCommit() + '\n');
            System.out.append("DB name: " + conn.getMetaData().getDatabaseProductName() + '\n');
            System.out.append("DB version: " + conn.getMetaData().getDatabaseProductVersion() + '\n');
            System.out.append("Driver: " + conn.getMetaData().getDriverName() + '\n');
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void disconnect()
    {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
