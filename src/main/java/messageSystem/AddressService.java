package messageSystem;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class AddressService {

    private List<Address> accountServices = new CopyOnWriteArrayList<>();
    private List<Address> gameMechanicsServices = new CopyOnWriteArrayList<>();

    private Iterator<Address> accountServicesIterator = accountServices.iterator();
    private Iterator<Address> gameMechanicsServicesIterator = accountServices.iterator();

    public void addAccountService(Address AS_address)
    {
        accountServices.add(AS_address);
    }

    public Address getAccountService()
    {
        if(!accountServicesIterator.hasNext())
            accountServicesIterator = accountServices.iterator();

        return accountServicesIterator.next();
    }

    public void addGameMechanicsService(Address AS_address)
    {
        gameMechanicsServices.add(AS_address);
    }

    public Address getGameMechanicsService()
    {
        if(!gameMechanicsServicesIterator.hasNext())
            gameMechanicsServicesIterator = accountServices.iterator();

        return gameMechanicsServicesIterator.next();
    }


}