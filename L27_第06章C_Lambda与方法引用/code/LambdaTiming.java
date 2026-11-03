import java.util.function.IntConsumer;
public class LambdaTiming {
    static void repeat(int n, IntConsumer action) {
        for (int i = 0; i < n; i++) action.accept(i);
    }
    public static void main(String[] args) {
        String message = "复习";
        Runnable action = () -> System.out.println(message);
        System.out.println("created");
        action.run();
        repeat(3, i -> System.out.println("第" + i + "次"));
    }
}
