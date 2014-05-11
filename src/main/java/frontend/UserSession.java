package frontend;

public class UserSession {

    private String sessionId;
    private String userName;
    private Long userId;


    public UserSession(String sid, String name, Long uid)
    {
        sessionId = sid;
        userName = name;
        userId = uid;
    }

    public String getSessionId()
    {
        return sessionId;
    }

    public String getName()
    {
        return userName;
    }


}