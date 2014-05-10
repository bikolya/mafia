package dbService;

public class User {
    private long id;
    private String name;
    private String pass;

    public User(long id, String name, String pass)
    {
        this.id = id;
        this.name = name;
        this.pass = pass;
    }

    public User(String name, String pass)
    {
        this.id = -1;
        this.name = name;
        this.pass = pass;
    }

    public long getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public String getPass()
    {
        return pass;
    }
}