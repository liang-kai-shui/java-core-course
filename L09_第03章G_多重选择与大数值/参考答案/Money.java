import java.math.BigDecimal;
import java.math.RoundingMode;
public class Money {
    public static void main(String[] args) {
        BigDecimal a = new BigDecimal("0.1");
        BigDecimal b = new BigDecimal("0.2");
        BigDecimal total = a.add(b);
        System.out.println(total);
        System.out.println(BigDecimal.ONE.divide(new BigDecimal("3"), 2, RoundingMode.HALF_UP));
    }
}
