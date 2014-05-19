package utils.resource;

public class ConnectionResources implements Resource {

    private String driver;
    private String host;
    private String port;
    private String db_name;
    private String user;
    private String password;

    public String getDriver() {
        return driver;
    }

    public String getHost() {
        return host;
    }

    public String getPort() {
        return port;
    }

    public String getDB_name() {
        return db_name;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }
}