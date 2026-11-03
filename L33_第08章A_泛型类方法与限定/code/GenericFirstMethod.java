public class GenericFirstMethod {
    static <T> T first(T[] items) {
        if (items.length == 0) throw new IllegalArgumentException("empty");
        return items[0];
    }
    public static void main(String[] args) {
        System.out.println(first(new String[]{"A", "B"}));
        System.out.println(first(new Integer[]{4, 5}));
    }
}
