import binance.BinanceConnectionWebSocket;

import java.net.URI;
import java.net.URISyntaxException;

public class Main {

    private static String URL = "wss://fstream.binance.com/stream?streams=btcusdt@aggTrade";

    public static void main(String[] args) {

        try {

            URI url = new URI(URL);
            BinanceConnectionWebSocket client = new BinanceConnectionWebSocket(url);
            client.connect();

            client.close();

        } catch (URISyntaxException e) {
            System.out.println(e.getMessage());
        }

    }
}
