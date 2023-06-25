package calculator;

import java.math.BigDecimal;
import java.math.BigInteger;

public class CalculatorForData {
    private BigInteger time;
    private BigInteger timeSub;
    private BigInteger timeValue;


    private BigDecimal price;
    private BigDecimal priceSub;
    private BigDecimal priceValue;

    public CalculatorForData(String price, String priceSub, String priceValue, String time, String timeSub, String timeValue) {
        this.price = new BigDecimal(price);
        this.priceSub = new BigDecimal(priceSub);
        this.priceValue = new BigDecimal(priceValue);
        this.time = new BigInteger(time);
        this.timeSub = new BigInteger(timeSub);
        this.timeValue = new BigInteger(timeValue);
    }

    private boolean checkTime() {

        int res1 = time.subtract(timeSub).compareTo(timeValue);
        int res2 = time.subtract(timeSub).compareTo(timeValue.negate());

        return (res1 <= 0) && (res2 >= 0);
    }

    private boolean checkPrice() {

        if((!price.equals("0")) || (!price.equals("0.0"))) {
            int res = price.subtract(priceSub).compareTo(priceValue);
            return res >= 0;
        }

        return false;
    }

    public boolean checkData() {
        return checkPrice() && checkTime();
    }

}
