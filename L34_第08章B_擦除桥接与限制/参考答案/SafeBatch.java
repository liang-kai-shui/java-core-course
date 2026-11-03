import java.util.function.IntFunction;
import java.util.function.Supplier;
public class SafeBatch {
    static <T> T create(Supplier<T> factory) { return factory.get(); }
    static <T> T[] emptyArray(int n, IntFunction<T[]> factory) {
        if (n < 0) throw new IllegalArgumentException("negative length");
        return factory.apply(n);
    }
    public static void main(String[] args) {
        StringBuilder value = create(StringBuilder::new);
        value.append("Java");
        String[] slots = emptyArray(2, String[]::new);
        System.out.println(value);
        System.out.println(slots.length);
        System.out.println(slots[0]);
        System.out.println(emptyArray(0, String[]::new).length);
        try { emptyArray(-1, String[]::new); }
        catch (IllegalArgumentException ex) { System.out.println("negative rejected"); }
    }
}
