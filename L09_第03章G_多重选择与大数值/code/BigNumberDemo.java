import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
public class BigNumberDemo {
    public static void main(String[] args) {
        BigInteger huge = new BigInteger("9223372036854775808");
        System.out.println(huge.add(BigInteger.ONE));
        BigDecimal a = new BigDecimal("0.1");
        BigDecimal b = new BigDecimal("0.2");
        System.out.println(a.add(b));
        BigDecimal third = BigDecimal.ONE.divide(new BigDecimal("3"), 2, RoundingMode.HALF_UP);
        System.out.println(third);
        BigDecimal x = new BigDecimal("1.0");
        BigDecimal y = new BigDecimal("1.00");
        System.out.println("equals=" + x.equals(y));
        System.out.println("compare=" + x.compareTo(y));
    }
}
