package messageSystem.messages;

import accountService.AccountService;
import messageSystem.Address;
import messageSystem.Message;

public class MsgRegisterUser extends MsgToAccountService {

    private String name;
    private String pass;
    private String sessionId;

    public MsgRegisterUser(Address from, Address to, String name, String pass, String sessionId)
    {
        super(from, to);
        this.name = name;
        this.pass = pass;
        this.sessionId = sessionId;
    }

    public void exec(AccountService accountService)
    {
        Message back;
        if(accountService.registerUser(name, pass)) {
            back = new MsgUpdateUserId(getTo(), getFrom(), sessionId, accountService.getUserId(name));
        }
        else
            back = new MsgUpdateUserId(getTo(), getFrom(), sessionId, -1);
        accountService.getMessageSystem().sendMessage(back);
    }
}