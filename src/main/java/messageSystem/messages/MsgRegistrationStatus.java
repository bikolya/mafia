package messageSystem.messages;

import frontend.Frontend;
import messageSystem.Address;

public class MsgRegistrationStatus extends MsgToFrontend{

    private String sessionId;
    private Long userId;

    public MsgRegistrationStatus(Address from, Address to, String sessionId, Long userId)
    {
        super(from, to);
        this.sessionId = sessionId;
        this.userId = userId;
    }

    public void exec(Frontend frontend)
    {
        frontend.updateStatus(sessionId, userId);
    }
}