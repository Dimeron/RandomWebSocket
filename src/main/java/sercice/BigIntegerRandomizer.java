package sercice;

import dto.Message;

import java.math.BigInteger;
import java.util.*;

public class BigIntegerRandomizer implements Randomizer {

    @Override
    public Message provideMessage(String ip, Set<BigInteger> numberSet, Map<String, BigInteger> messageStorage) {
        // if we already have the number - get it from storage
        BigInteger result = messageStorage.get(ip);
        // else generate it
        if (result == null) {
            Random rand = new Random();
            result = new BigInteger(Integer.SIZE, rand);
            // while we have already used the number
            while (numberSet.contains(result)) {
                // generate a new one because it's already used
                result = new BigInteger(Integer.MAX_VALUE, rand);
            }
            numberSet.add(result);
            messageStorage.put(ip, result);
        }
        return new Message(result.toString());
    }
}
