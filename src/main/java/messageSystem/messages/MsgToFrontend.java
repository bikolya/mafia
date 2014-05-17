package messageSystem.messages;

import accountService.AccountService;
import frontend.Frontend;
import messageSystem.Address;
import messageSystem.Message;
import messageSystem.Subscriber;

public  abstract class MsgToFrontend extends Message{

    public MsgToFrontend(Address from, Address to)
    {
        super(from, to);
    }

    public void exec(Subscriber subscriber)
    {
        if(subscriber instanceof Frontend){
            exec( (Frontend) subscriber);
        }
    }

    public abstract void exec(Frontend frontend);
}