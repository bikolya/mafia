package messageSystem;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class AddressService {

    private List<Address> services = new CopyOnWriteArrayList<>();

    private Iterator<Address> iter = services.iterator();

    public void addAccountService(Address AS_address)
    {
        services.add(AS_address);
    }

    public Address getAccountService()
    {
        if(!iter.hasNext())
            iter = services.iterator();

        return iter.next();
    }



}