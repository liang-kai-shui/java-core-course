import java.util.function.Function;
import java.util.function.IntConsumer;
public class LambdaPractice {
    static void repeat(int n, IntConsumer action) { for (int i = 0; i < n; i++) action.accept(i); }
    public static void main(String[] args) {
        String prefix = "练习";
        repeat(2, i -> System.out.println(prefix + i));
        repeat(0, i -> System.out.println(prefix + i));
        Function<String, Label> factory = Label::new;
        System.out.println(factory.apply("数组"));
    }
}
class Label {
    private final String text;
    Label(String text) { this.text = text; }
    @Override public String toString() { return "标签:" + text; }
}
