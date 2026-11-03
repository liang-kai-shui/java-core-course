public class SharedCounterEdit {
    static void addThree(VisitCounter received) { received.add(3); }
    public static void main(String[] args) {
        VisitCounter original = new VisitCounter(5);
        VisitCounter alias = original;
        System.out.println("before=" + original.get());
        addThree(original);
        System.out.println("original=" + original.get());
        System.out.println("alias=" + alias.get());
    }
}
class VisitCounter {
    private int value;
    VisitCounter(int value) { this.value = value; }
    int get() { return value; }
    void add(int amount) { value += amount; }
}
