public class PairPractice {
    static <T> T last(T[] items) {
        if (items.length == 0) throw new IllegalArgumentException("empty");
        return items[items.length - 1];
    }
    public static void main(String[] args) {
        PairBox<String> pair = new PairBox<>("读", "练");
        System.out.println(pair.first() + ":" + pair.second());
        PairBox<String> other = pair.swapped();
        System.out.println(other.first() + ":" + other.second());
        System.out.println(pair.first() + ":" + pair.second());
        System.out.println(last(new String[]{"A", "B"}));
        System.out.println(last(new Integer[]{1}));
        try { last(new String[0]); }
        catch (IllegalArgumentException ex) { System.out.println("empty rejected"); }
    }
}
class PairBox<T> {
    private final T first, second;
    PairBox(T first, T second) { this.first = first; this.second = second; }
    T first() { return first; }
    T second() { return second; }
    PairBox<T> swapped() { return new PairBox<>(second, first); }
}
