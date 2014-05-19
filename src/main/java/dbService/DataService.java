package dbService;

import utils.resource.ConnectionResources;
import utils.resource.ResourceFactory;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataService {
    private Connection conn = null;
    private ConnectionResources connResources = (ConnectionResources) ResourceFactory.getInstance().get("src/main/resources/connection.xml");

    public Connection getConnection()
    {
        if (conn != null) {
            return conn;
        } else
            try {
                DriverManager.registerDriver((Driver) Class.forName("com.mysql.jdbc.Driver").newInstance());
                return DriverManager.getConnection(
                        "jdbc:mysql://" +
                        "localhost:" +
                        connResources.getPort() + "/" +
                        connResources.getDB_name() + "?" +
                        "user=" + connResources.getUser() +"&" +
                        "password=" + connResources.getPassword());

            } catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                e.printStackTrace();
        }
        return null;
    }
}
