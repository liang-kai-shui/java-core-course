public class ArgsEcho {
    public static void main(String[] args) {
        System.out.println("count=" + args.length);
        for (String argument : args) {
            System.out.println(argument);
        }
    }
}
