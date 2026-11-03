package campus.app;
import campus.model.Counter;
import campus.model.Inspector;
public class Main {
    public static void main(String[] args) {
        Counter c = new Counter();
        System.out.println("public=" + c.read());
        System.out.println("same-package=" + Inspector.inspect(c));
    }
}
