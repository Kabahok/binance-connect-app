package binance;

// Тут подключаются все зависимости в наш класс
import calculator.CalculatorForData;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.List;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

// Реализуем класс, который будет подкючаться к потоку бинанса и получать данные
public class BinanceConnectionWebSocket extends WebSocketClient {

//    Задаются переменные, в которых будут хранится все данные для отдельного потока

    private List<String> dataList;
    private int numberStream;

//    В конструктор из Main передаются все данные: Url, данные для вычислений
    public BinanceConnectionWebSocket(URI url, List<String> dataList, int numberStream) {
        super(url);
        this.dataList = dataList;
        this.numberStream = numberStream;
    }

//    Если подключение успешно, то срабатывает этот метод и выводится сообщение в формате: Поток "номер": Подключение успешно
    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        System.out.printf("Поток %d: Подключение успешно\n", numberStream);
    }

//    Метод, который срабатывает при получении данных с бинанса( данные приходят в виде строка и передаются в параметр s )
    @Override
    public void onMessage(String s) {

// Ловим исключение, если у нас парсинг его выкинет( программа не упадет, продолжит работать и выведет соответсвующее сообщение, которое прописано в блоке catch() )
        try {

//          Получаем обработанные данные
            JSONObject item = parseDateFromJSON(s);

//          Создаем объект калькулятора и передаем ему все данные, которые необходимы для проверки( в классе калькулятора пояснил за что отвечает каждый параметр)
            CalculatorForData c = new CalculatorForData(((String) item.get("p")), dataList.get(2), dataList.get(1), String.valueOf(item.get("E")), dataList.get(4), dataList.get(3));

//           Вызываем метод нашего калькулятора, который проверяет пару, если все подходит, то выводит сообщение: Номер потока | Значение времени | Значение цены
            if (c.checkData()) {
                System.out.printf("Поток %d | Время: %s | Цена: %s%n", numberStream, item.get("E"), item.get("p").toString());
            } else {
//            Тут вы можете прописать, что делать в случае, если пара не подходит(вывести например какое-то сообщение)
            }

//      Тут выведется сообщение, если обработка данных не получилась(Обычно она выведется, если данные пришли не в том формате, но на проверках этот блок ниразу не сработал)
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
    }


//    Метод, который срабатывает при закрытии соединения(у вас он срабатывать не будет, так как вы останавливаете программу вручную)
    @Override
    public void onClose(int i, String s, boolean b) {
        System.out.println("Connection is close");
    }

//    Метод, который выводит сообщение об ошибке, если такая появится при подключении
    @Override
    public void onError(Exception e) {
        System.out.println(e.getMessage());
    }


//    Метод, который парсит данные с бинанса для дальнейшей работы( выкинет исключение ParseException, если парсинг невозможен)
    public static JSONObject parseDateFromJSON(String jsonString) throws ParseException {
        JSONParser parser = new JSONParser();
        return (JSONObject) ((JSONObject) parser.parse(jsonString)).get("data");
    }
}
