package binance;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;


public class BinanceConnectionWebSocket extends WebSocketClient {

    public BinanceConnectionWebSocket(URI url) {
        super(url);
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        System.out.println(serverHandshake.getHttpStatus());
    }

    @Override
    public void onMessage(String s) {
        System.out.println(s);
    }

    @Override
    public void onClose(int i, String s, boolean b) {
        System.out.println("Connection is close");
    }

    @Override
    public void onError(Exception e) {
        System.out.println("Error");
    }
}
