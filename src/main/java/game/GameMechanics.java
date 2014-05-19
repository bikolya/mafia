package game;

import messageSystem.Address;
import messageSystem.MessageSystem;
import messageSystem.Subscriber;

public class GameMechanics implements Runnable, Subscriber {

    private final MessageSystem messageSystem;
    private final Address address;

    public GameMechanics(MessageSystem ms)
    {
        messageSystem = ms;
        address = new Address();
        messageSystem.registerService(this);
    }


    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public MessageSystem getMessageSystem() {
        return messageSystem;
    }

    @Override
    public void run() {
        while(true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            messageSystem.execForSubscriber(this);
        }
    }
}