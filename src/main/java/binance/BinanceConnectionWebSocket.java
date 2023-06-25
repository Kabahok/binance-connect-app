package binance;

import calculator.CalculatorForData;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.List;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class BinanceConnectionWebSocket extends WebSocketClient {

    private List<String> dataList;
    private int numberStream;

    public BinanceConnectionWebSocket(URI url, List<String> dataList, int numberStream) {
        super(url);
        this.dataList = dataList;
        this.numberStream = numberStream;
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        System.out.println("Подключение успешно");
    }

    @Override
    public void onMessage(String s) {

        try {

            JSONObject item = parseDateFromJSON(s);

            CalculatorForData c = new CalculatorForData(((String) item.get("p")), dataList.get(2), dataList.get(1), String.valueOf(item.get("E")), dataList.get(4), dataList.get(3));

            if (c.checkData()) {
                System.out.println(item.get("p") + " " + item.get("E"));
            } else {
                System.out.printf("Поток %d | Время: %s | Цена: %s%n", numberStream, item.get("E"), item.get("p").toString());
            }

        } catch (ParseException e) {
            System.out.println(e.getMessage());
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
