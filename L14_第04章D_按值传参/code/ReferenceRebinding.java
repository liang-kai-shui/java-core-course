public class ReferenceRebinding {
    static void replace(RebindCounter received) {
        received = new RebindCounter(99);
        System.out.println("inside=" + received.get());
    }
    public static void main(String[] args) {
        RebindCounter original = new RebindCounter(5);
        System.out.println("before=" + original.get());
        replace(original);
        System.out.println("after=" + original.get());
    }
}
class RebindCounter {
    private final int value;
    RebindCounter(int value) { this.value = value; }
    int get() { return value; }
}
