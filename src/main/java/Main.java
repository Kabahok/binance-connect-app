import binance.BinanceConnectionWebSocket;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;


public class Main {
//    ------!!!!!! Перебарщивать с потоками не стоит, так как их работа требует много ресурсов !!!!!!------

//    В этой переменной вам необходимо занести все данные для каждого потока (каждый вложенный список это данные для одного потока,
//    то есть сколько тут вложенных списков, столько и будет поток

//    Каждый вложенный список должен иметь 5 значений, в строгом порядке: Ссылка на поток; отклонение для цены; значение которое будет вычитаться из цены; отклонение для времени; значение, которое будет вычитаться из времени

    private static List<List<String>> dataSet = Arrays.asList(
//                                          Ссылка 0                                priseValue 1  priceSub 2 timeValue 3 timeSub 4
            Arrays.asList("wss://fstream.binance.com/stream?streams=xlmusdt@aggTrade", "0.01000", "25000.02", "900000", "1000000"),
            Arrays.asList("wss://fstream.binance.com/stream?streams=zecusdt@aggTrade", "0.80", "25000.02", "900000", "1000000"),
            Arrays.asList("wss://fstream.binance.com/stream?streams=ontusdt@aggTrade", "0.005", "25000.02", "900000", "1000000"),
            Arrays.asList("wss://fstream.binance.com/stream?streams=btcusdt@aggTrade", "0.005", "25000.02", "900000", "1000000"),
            Arrays.asList("wss://fstream.binance.com/stream?streams=dotusdt@aggTrade", "0.1", "25000.02", "900000", "1000000")
    );

    public static void main(String[] args) {

        for (int i = 0; i < dataSet.size(); i++) {
            Thread thread = new Thread(new personalStream(dataSet.get(i), i + 1));
            thread.start();
        }

    }
}


// Тут прописана логика для отдельного потока

class personalStream implements Runnable {

    private List<String> dataList;
    private int numberStream;

    public personalStream(List<String> dataList, int numberStream) {
        this.dataList = dataList;
        this.numberStream = numberStream;
    }

    @Override
    public void run() {

        try {

            BinanceConnectionWebSocket client = new BinanceConnectionWebSocket(new URI(dataList.get(0)), dataList, numberStream);
            client.connect();


        } catch (URISyntaxException e) {
            System.out.println(e.getMessage());
        }
    }

}
