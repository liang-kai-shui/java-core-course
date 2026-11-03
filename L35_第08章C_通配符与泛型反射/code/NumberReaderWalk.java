import java.util.*;
public class NumberReaderWalk {
    static double sum(List<? extends Number> values) { double result = 0.0; for (Number value : values) result += value.doubleValue(); return result; }
    public static void main(String[] args) {
        System.out.println(sum(Arrays.asList(1, 2))); System.out.println(sum(Arrays.asList(0.5, 1.5))); System.out.println(sum(Collections.<Number>emptyList()));
    }
}
