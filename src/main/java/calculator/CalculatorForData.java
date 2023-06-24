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

    public boolean checkTime() {

        int res1 = time.subtract(timeSub).compareTo(timeValue);
        int res2 = time.subtract(timeSub).compareTo(timeValue.negate());

        System.out.println(time.subtract(timeSub));
        System.out.println(res1);
        System.out.println(res2);

        return (res1 <= 0) && (res2 >= 0);
    }

    public boolean checkPrice() {

        if(Float.parseFloat(price.toString()) != 0) {
            int res = price.subtract(priceSub).compareTo(priceValue);

            System.out.println(price.subtract(priceSub));

            return res >= 0;
        }

        return false;
    }

    public boolean checkData() {
        return checkPrice() && checkTime();
    }

    public void test() {

    }
}
