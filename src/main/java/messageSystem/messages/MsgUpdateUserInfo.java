package messageSystem.messages;

import frontend.Frontend;
import messageSystem.Address;

public class MsgUpdateUserInfo extends MsgToFrontend{

    private String sessionId;
    private String name;
    private long userId;

    public MsgUpdateUserInfo(Address from, Address to, String sessionId, String name, long userId)
    {
        super(from, to);
        this.sessionId = sessionId;
        this.name = name;
        this.userId = userId;
    }

    public void exec(Frontend frontend)
    {
        frontend.updateUserInfo(sessionId , name, userId);
    }

}