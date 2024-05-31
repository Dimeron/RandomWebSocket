import dto.Message;
import dto.MessageDecoder;
import dto.MessageEncoder;
import sercice.BigIntegerRandomizer;
import sercice.Randomizer;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@ServerEndpoint(value = "/random", decoders = MessageDecoder.class, encoders = MessageEncoder.class)
public class RandomEndpoint {
    private static Set<BigInteger> numberSet = Collections.synchronizedSet(new HashSet<>());
    private static ConcurrentMap<String, BigInteger> messageStorage = new ConcurrentHashMap<>();
    // ability to choose implementation
    private final Randomizer randomizer = new BigIntegerRandomizer();

    /**
     * Container calls this method when connection is established.
     */
    @OnOpen
    public void onOpen(Session session) throws IOException {
        System.out.println("Session Open [" + session.getId() + "]");
    }

    /**
     * Container calls this method when websocket connection is closed by browser or
     * server.
     */
    @OnClose
    public void onClose(Session session) {
        System.out.println("Session Close [" + session.getId() + "]");
    }

    /**
     * Process message received from browser.
     */
    @OnMessage
    public void onMessage(String ip, Session session) throws IOException, EncodeException {
        System.out.println("Received ip [" + ip + "]");
        // generate number
        Message answer = randomizer.provideMessage(ip, numberSet, messageStorage);
        // send the message
        session.getBasicRemote().sendObject(answer);
    }

    /**
     * Container calls this method when there is an error in websocket
     * communication.
     */
    @OnError
    public void onError(Throwable t) {
        System.err.println("Error - " + t.getMessage());
    }
}