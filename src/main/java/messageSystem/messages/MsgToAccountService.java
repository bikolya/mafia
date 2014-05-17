package messageSystem.messages;

import accountService.AccountService;
import messageSystem.Address;
import messageSystem.Message;
import messageSystem.Subscriber;

public abstract class MsgToAccountService extends Message {
    public MsgToAccountService(Address from, Address to)
    {
        super(from, to);
    }

    public void exec(Subscriber subscriber)
    {
        if(subscriber instanceof AccountService){
            exec( (AccountService) subscriber);
        }
    }

    public abstract void exec(AccountService accountService);
}