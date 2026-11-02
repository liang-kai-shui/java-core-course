package campus.app;

import campus.text.JarGreeting;

public class JarMain {
    public static void main(String[] args) {
        String name = args.length == 0 ? "Lin" : args[0];
        System.out.println(JarGreeting.forName(name));
    }
}
