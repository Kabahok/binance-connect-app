package calculator;

// Подклчаем все зависимости
import java.math.BigDecimal;
import java.math.BigInteger;

public class CalculatorForData {
//    Объявляем переменные для хранения данных, для удобного вычисления

    private BigInteger time; // Значение времени, которое приходит с бинанса
    private BigInteger timeSub; // Значение, которое будет вычитаться из времени
    private BigInteger timeValue; // Значение, с которым будет сравниваться разница


    private BigDecimal price; // Значение цены, которое приходит с бинанса
    private BigDecimal priceSub; // Значение, которое будет вычитаться из времени
    private BigDecimal priceValue; // Значение, с которым будет сравниваться разница

//  С помощью конструктора инициализируем наши переменные, присваивая соответственные значения

//  Все данные изначально приходят в виде строки, чтобы сохранить значения в том виде, в котором они пришли

//   Так как цена является нецелым числом, то для корректного вычисления не получится использовать примитивные данные по типу double или float
//   Поэтому используются отдельные классы для работы со временем и ценной

//   Для цены BigDecimal, что корректно вычитать дробные значения и сравнивать их
//   Для времени BigInteger, чтобы производить вычисления с огромными значениями

    public CalculatorForData(String price, String priceSub, String priceValue, String time, String timeSub, String timeValue) {
        this.price = new BigDecimal(price);
        this.priceSub = new BigDecimal(priceSub);
        this.priceValue = new BigDecimal(priceValue);
        this.time = new BigInteger(time);
        this.timeSub = new BigInteger(timeSub);
        this.timeValue = new BigInteger(timeValue);
    }

//    Метод, который проверяет время
    private boolean checkTime() {


//  time.subtract(timeSub) так вычисляется разница
//  compareTo(timeValue) потом применяется метод для сравнения с значением(правая граница отрезка, то есть положительная). Если разница больше то получим 1, если равна то 0, если меньше то -1
//  compareTo(timeValue.negate()) тоже самое, только сравнивается уже с левой границей, то есть отрицательным значением

        int res1 = time.subtract(timeSub).compareTo(timeValue);
        int res2 = time.subtract(timeSub).compareTo(timeValue.negate());

//      res1 = time.subtract(timeSub).compareTo(timeValue) тут сравниваем с правой границей, то есть должно быть <=, значит должно получиться значение -1 или 0
//      res2 = time.subtract(timeSub).compareTo(timeValue.negate()) тут с левой границей, то есть >=, значит должно получиться 1 или 0

//      Если оба условия выполняются, то время подходит и возвращается true, иначе вернется false
        return (res1 <= 0) && (res2 >= 0);
    }


//   Метод для прроверки цены
    private boolean checkPrice() {

//      Сначала проверяем, чтобы цена была не равна 0
        if((!price.equals("0")) || (!price.equals("0.0"))) {

//          Тут аналогичные вычисления, как со временем, проверяем, чтобы разница была больше указнного значения
            int res = price.subtract(priceSub).compareTo(priceValue);
            return res >= 0;

        }

        return false;
    }

//   Итоговый метод, который проверяет, чтобы подходила и цена и время(который мы как раз и вызываем к классе BinanceConnectionWebSocket
    public boolean checkData() {
        return checkPrice() && checkTime();
    }

}
