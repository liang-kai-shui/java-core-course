import java.lang.reflect.Array;
import java.util.Arrays;
public class TypedArrayResize {
    static Object resize(Object source, int newLength) {
        Class<?> type = source.getClass();
        if (!type.isArray() || newLength < 0) throw new IllegalArgumentException("array and nonnegative length required");
        Object result = Array.newInstance(type.getComponentType(), newLength);
        System.arraycopy(source, 0, result, 0, Math.min(Array.getLength(source), newLength));
        return result;
    }
    public static void main(String[] args) {
        int[] numbers = {2, 4};
        String[] words = {"A"};
        System.out.println(Arrays.toString((int[]) resize(numbers, 4)));
        String[] expanded = (String[]) resize(words, 2);
        System.out.println(Arrays.toString(expanded));
        System.out.println(Arrays.toString((String[]) resize(expanded, 0)));
    }
}
