package sercice;

import dto.Message;

import java.math.BigInteger;
import java.util.Map;
import java.util.Set;

public interface Randomizer {
    Message provideMessage(String ip, Set<BigInteger> numberSet, Map<String, BigInteger> messageStorage);
}
