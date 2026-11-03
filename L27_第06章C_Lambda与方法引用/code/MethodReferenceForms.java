import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
public class MethodReferenceForms {
    public static void main(String[] args) {
        Consumer<String> printer = System.out::println;
        Function<String, Integer> parser = Integer::parseInt;
        Comparator<String> order = String::compareToIgnoreCase;
        IntFunction<String[]> factory = String[]::new;
        printer.accept("hello");
        System.out.println(parser.apply("12"));
        System.out.println(order.compare("a", "B") < 0);
        System.out.println(factory.apply(2).length);
    }
}
