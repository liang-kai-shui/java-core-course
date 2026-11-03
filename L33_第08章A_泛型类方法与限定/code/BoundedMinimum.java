public class BoundedMinimum {
    static <T extends Comparable<T>> T min(T[] values) {
        if (values.length == 0) throw new IllegalArgumentException("empty");
        T best = values[0];
        for (T candidate : values) if (candidate.compareTo(best) < 0) best = candidate;
        return best;
    }
    public static void main(String[] args) {
        System.out.println(min(new String[]{"C", "A", "B"}));
        System.out.println(min(new Integer[]{-2, -5, -1}));
    }
}
