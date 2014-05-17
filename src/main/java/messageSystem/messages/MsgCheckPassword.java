package messageSystem.messages;

import accountService.AccountService;
import messageSystem.Address;
import messageSystem.Message;

public class MsgCheckPassword extends MsgToAccountService {

    private String name;
    private String pass;
    private String sessionId;

    public MsgCheckPassword(Address from, Address to, String name, String pass, String sessionId)
    {
        super(from, to);
        this.name = name;
        this.pass = pass;
        this.sessionId = sessionId;
    }

    public void exec(AccountService accountService)
    {
        long id = -1;
        if(accountService.checkPassword(name, pass))
            id = accountService.getUserId(name);

        Message back = new MsgUpdateUserInfo(getTo(), getFrom(), sessionId, name, id);
        accountService.getMessageSystem().sendMessage(back);
    }
}