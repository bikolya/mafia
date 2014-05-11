package frontend;

import java.math.BigInteger;
import java.security.SecureRandom;

public class SessionIdGen {

    private SecureRandom random = new SecureRandom();

    public String nextSessionId() {
        BigInteger b = new BigInteger(255, random);
        String s = b.toString();
        return s;
    }
}
