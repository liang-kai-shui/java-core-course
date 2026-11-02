package campus.app;

import campus.text.Greeting;

public class Main {
    public static void main(String[] args) {
        String name = args.length == 0 ? "Lin" : args[0];
        System.out.println(Greeting.forName(name));
    }
}
