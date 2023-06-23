package calculator;

import java.math.BigDecimal;

public class Calculator {

//    Переменные, в которые будут передаваться значения, для расчета с временем
    private long time;
    private long timeRazn;
    private long timeValue = 900_000L;

//    Переменные, в который будут передаваться значеня, для расчета с ценой
    private BigDecimal price;
    private BigDecimal priceRazn;
    private BigDecimal priceValue = new BigDecimal("0.0002");

    public Calculator(String time, String price, long timeRazn, String priceRazn) {
        this.time = Long.parseLong(time);
        this.price = new BigDecimal(price);
        this.timeRazn = timeRazn;
        this.priceRazn = new BigDecimal(priceRazn);

    }

    private boolean calcTime() {
        if ((time - timeRazn) < timeValue && ((time - timeRazn)) > -timeValue) {
            return true;
        }

        return false;
    }

    private boolean calcPrice() {
        int res = price.subtract(priceRazn).compareTo(priceValue);
        if (res > 0) {
            return true;
        }
        return false;
    }

    public boolean checkData() {
        return (calcPrice() && calcTime());
    }
}
