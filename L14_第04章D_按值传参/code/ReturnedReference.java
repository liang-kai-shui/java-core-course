public class ReturnedReference {
    static NextCounter nextCopy(NextCounter received) { return new NextCounter(received.get() + 1); }
    public static void main(String[] args) {
        NextCounter original = new NextCounter(5);
        NextCounter alias = original;
        nextCopy(original);
        System.out.println("ignored=" + original.get());
        original = nextCopy(original);
        System.out.println("new=" + original.get());
        System.out.println("alias=" + alias.get());
    }
}
class NextCounter {
    private final int value;
    NextCounter(int value) { this.value = value; }
    int get() { return value; }
}
