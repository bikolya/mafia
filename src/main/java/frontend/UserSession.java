package frontend;

import java.util.Date;

public class UserSession {

    private String sessionId;
    private String userName;
    private Long userId;
    private Date lastAction;


    public UserSession(String sid, String name, Long uid)
    {
        sessionId = sid;
        userName = name;
        userId = uid;
    }

    public UserSession(String id)
    {
        sessionId = id;
        userName = null;
        userId = null;
    }

    public String getSessionId()
    {
        return sessionId;
    }

    public String getName()
    {
        return userName;
    }

    public void setName(String name)
    {
        userName = name;
    }

    public void setUserId(Long id)
    {
        userId = id;
    }

    public Long getUserId()
    {
        return userId;
    }

    public Date getLastAction() {
        return lastAction;
    }

    public void setLastAction(Date lastAction) {
        this.lastAction = lastAction;
    }
}