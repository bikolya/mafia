package messageSystem;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class AddressService {

    private Map<Class, List<Address>> services = new ConcurrentHashMap<>();
    private Map<Class, Iterator<Address>> iterators = new ConcurrentHashMap<>();

    public Address getService(Class clazz)
    {
        Iterator<Address> iterator = iterators.get(clazz);

        if(!iterator.hasNext())
            iterator = services.get(clazz).iterator();

        return iterator.next();
    }

    public void addService(Subscriber subscriber)
    {
        if( services.get(subscriber.getClass()) == null) {
            services.put(subscriber.getClass(), new CopyOnWriteArrayList<Address>());
            iterators.put(subscriber.getClass(), services.get(subscriber.getClass()).iterator());
        }
        services.get(subscriber.getClass()).add(subscriber.getAddress());
    }


}