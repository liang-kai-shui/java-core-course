public class InitSequence {
    public static void main(String[] args) {
        new Session();
        new Session("自选");
    }
}
class Session {
    static { System.out.println("static"); }
    private int value = seed();
    { value++; System.out.println("block:" + value); }
    Session() { this("默认"); System.out.println("outer"); }
    Session(String label) { System.out.println("body:" + label); }
    private static int seed() { System.out.println("field"); return 3; }
}
