package messageSystem.messages;

import accountService.AccountService;
import messageSystem.Address;
import messageSystem.Message;

public class MsgGetUserId extends MsgToAccountService{

    private String name;
    private String sessionId;

    public MsgGetUserId(Address from, Address to, String name, String sessionId)
    {
        super(from, to);
        this.name = name;
        this.sessionId = sessionId;
    }

    public void exec(AccountService accountService)
    {
        long id = accountService.getUserId(name);
        Message back = new MsgUpdateUserId(getTo(), getFrom(), sessionId, id);
        accountService.getMessageSystem().sendMessage(back);
    }
}