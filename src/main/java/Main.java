import binance.BinanceConnectionWebSocket;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;


public class Main {

//    ------!!!!!! Перебарщивать с потоками не стоит, так как их работа требует много ресурсов !!!!!!------

//    В этой переменной вам необходимо занести все данные для каждого потока (каждый вложенный список это данные для одного потока,
//    то есть сколько тут вложенных списков, столько и будет потоков)

//    Каждый вложенный список должен иметь 5 значений, в строгом порядке: Ссылка на поток; отклонение для цены; значение которое будет вычитаться из цены; отклонение для времени; значение, которое будет вычитаться из времени
//    Я заполнил тестовыми значениями, Вы укажите свои

//    ВАЖНО: следите, чтобы в ссылке не было пробелов, иначе не скомпилируется URL;
//    в числовых значениях тоже следите, чтобы не было пробелов и других инных символов(у значений для цены можно использовать точку, даже нужно, так это дробное значение, даже если у вас целое число, необходимо прописывать не просто 1, например, а 1.0)

//    Подписал, какие значения необходимо указывать, чтобы они правильно потом присвоились в классе калькулятора
    private static List<List<String>> dataSet = Arrays.asList(
//                                          Ссылка                                     priseValue   priceSub  timeValue  timeSub
            Arrays.asList("wss://fstream.binance.com/stream?streams=xlmusdt@aggTrade", "0.01000", "25000.02", "900000", "1000000"),
            Arrays.asList("wss://fstream.binance.com/stream?streams=zecusdt@aggTrade", "0.80", "25000.02", "900000", "1000000"),
            Arrays.asList("wss://fstream.binance.com/stream?streams=ontusdt@aggTrade", "0.005", "25000.02", "900000", "1000000"),
            Arrays.asList("wss://fstream.binance.com/stream?streams=btcusdt@aggTrade", "0.005", "25000.02", "900000", "1000000"),
            Arrays.asList("wss://fstream.binance.com/stream?streams=dotusdt@aggTrade", "0.1", "25000.02", "900000", "1000000")

    );

//    Тут собственно и заускается программа
    public static void main(String[] args) {

//        Цикл для перебора всех потоков в вышеуказнном списке
        for (int i = 0; i < dataSet.size(); i++) {

//          Создаем новый поток, передавая ему объект класса, который реализует действие потока(этому объекту передаем все значения потока, то есть отдельный вложенный список и номер потока,
//          при выполнении потоки запускаются в разном порядке, сути не играет, они работать будут параллельно
            Thread thread = new Thread(new personalStream(dataSet.get(i), i + 1));

//          Запускаем поток
            thread.start();
        }

    }
}


// Тут прописана логика для отдельного потока

class personalStream implements Runnable {

//  Задаем переменные, в которых сохранятся данные для каждого потока
    private List<String> dataList;
    private int numberStream;

//  Через конструктор записывем все значения
    public personalStream(List<String> dataList, int numberStream) {
        this.dataList = dataList;
        this.numberStream = numberStream;
    }

//  Метод, который запускается при старте потока(простыми словами прописываем что делает поток)
    @Override
    public void run() {

// Также ловим исключение(если ссылка неправильного формата, то выведется сообщение)
        try {

//          Создаем объект класса для подключения к потоку по ссылке, в качестве аргментов передаем значения: URL потока, список значения для потока, номер потока
            BinanceConnectionWebSocket client = new BinanceConnectionWebSocket(new URI(dataList.get(0)), dataList, numberStream);

//          Запускаем подключение по потоку к бинансу
            client.connect();

// Этот блок сработает, если неверный формат ссылки на поток будет
        } catch (URISyntaxException e) {
            System.out.println(e.getMessage());
        }
    }

}
