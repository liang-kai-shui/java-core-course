import java.util.function.IntFunction;
import java.util.function.Supplier;
public class TypedFactories {
    static <T> T make(Supplier<T> factory) { return factory.get(); }
    static <T> T[] makeArray(int n, IntFunction<T[]> factory) { return factory.apply(n); }
    public static void main(String[] args) {
        StringBuilder draft = make(StringBuilder::new);
        draft.append("ready");
        String[] values = makeArray(2, String[]::new);
        System.out.println(draft);
        System.out.println(values.length);
    }
}
