package binance;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class BinanceConnectionWebSocket extends WebSocketClient {

    public BinanceConnectionWebSocket(URI url) {
        super(url);
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {

    }

    @Override
    public void onMessage(String s) {
        try {
            System.out.println(parseDateFromJSON(s));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClose(int i, String s, boolean b) {
        System.out.println("Connection is close");
    }

    @Override
    public void onError(Exception e) {
        System.out.println(e.getMessage());
    }


    public static JSONObject parseDateFromJSON(String jsonString) throws ParseException {
        JSONParser parser = new JSONParser();
        return (JSONObject) ((JSONObject) parser.parse(jsonString)).get("data");
    }
}
