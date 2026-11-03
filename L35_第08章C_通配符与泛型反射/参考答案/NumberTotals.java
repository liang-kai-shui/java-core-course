import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class NumberTotals {
    static double sum(List<? extends Number> values) {
        double result = 0;
        for (Number value : values) result += value.doubleValue();
        return result;
    }
    static <T> void copyInto(List<? super T> target, List<? extends T> source) {
        for (T value : source) target.add(value);
    }
    static void swap(List<?> values) { swapCaptured(values); }
    private static <T> void swapCaptured(List<T> values) {
        if (values.size() < 2) throw new IllegalArgumentException("need two");
        T first = values.get(0); values.set(0, values.get(1)); values.set(1, first);
    }
    public static void main(String[] args) {
        List<Integer> source = Arrays.asList(1, 2);
        System.out.println(sum(source));
        System.out.println(sum(Arrays.asList(0.5, 1.5)));
        System.out.println(sum(new ArrayList<Number>()));
        List<Object> target = new ArrayList<>(); copyInto(target, source);
        System.out.println(target); System.out.println(source);
        swap(target); System.out.println(target);
        try { swap(new ArrayList<String>()); }
        catch (IllegalArgumentException ex) { System.out.println("need two"); }
    }
}
