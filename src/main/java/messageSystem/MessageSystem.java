package messageSystem;

import java.util.Map;
import java.util.HashMap;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MessageSystem {
    private Map<Address, ConcurrentLinkedQueue<Message>> messages = new HashMap<>();
    private AddressService addressService = new AddressService();

    public void sendMessage(Message message)
    {
        messages.get(message.getTo()).add(message);
    }

    public AddressService getAddressService()
    {
        return addressService;
    }

    public void execForSubscriber(Subscriber subscriber)
    {
        Queue<Message> mQueue = messages.get(subscriber.getAddress());
        while (!mQueue.isEmpty()) {
            Message message = mQueue.poll();
            message.exec(subscriber);
        }
    }

    public void registerService(Subscriber subscriber)
    {
        addressService.addService(subscriber);
        messages.put(subscriber.getAddress(), new ConcurrentLinkedQueue<Message>());
    }

}