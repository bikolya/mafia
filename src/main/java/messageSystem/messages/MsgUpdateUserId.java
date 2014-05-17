package messageSystem.messages;

import accountService.AccountService;
import frontend.Frontend;
import messageSystem.Address;
import messageSystem.Message;

public class MsgUpdateUserId extends MsgToFrontend{

    private String sessionId;
    private long userId;

    public MsgUpdateUserId(Address from, Address to, String sessionId, long userId)
    {
        super(from, to);
        this.sessionId = sessionId;
        this.userId = userId;
    }

    public void exec(Frontend frontend)
    {
        frontend.updateUserId(sessionId , userId);
    }

}