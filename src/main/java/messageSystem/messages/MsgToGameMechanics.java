package messageSystem.messages;

import accountService.AccountService;
import game.GameMechanics;
import messageSystem.Address;
import messageSystem.Message;
import messageSystem.Subscriber;

/**
 * Created by Andrey
 * 02.04.14.
 */
public abstract class MsgToGameMechanics extends Message {

    public MsgToGameMechanics(Address from, Address to)
    {
        super(from, to);
    }

    public void exec(Subscriber subscriber)
    {
        if(subscriber instanceof GameMechanics){
            exec( (GameMechanics) subscriber);
        }
    }

    public abstract void exec(GameMechanics accountService);
}