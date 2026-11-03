import java.util.ArrayList;
public class ErasureIdentity {
    public static void main(String[] args) {
        ErasedBox<String> a = new ErasedBox<>("Java");
        ErasedBox<Integer> b = new ErasedBox<>(2);
        System.out.println(a.getClass() == b.getClass());
        System.out.println(new ArrayList<String>().getClass() == new ArrayList<Integer>().getClass());
    }
}
class ErasedBox<T> {
    private final T value;
    ErasedBox(T value) { this.value = value; }
    T get() { return value; }
}
