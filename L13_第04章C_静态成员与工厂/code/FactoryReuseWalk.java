public class FactoryReuseWalk {
    public static void main(String[] args) {
        Notice a = Notice.shared();
        Notice b = Notice.shared();
        Notice c = Notice.fresh();
        System.out.println(a == b);
        System.out.println(a == c);
        System.out.println(c.text());
    }
}
class Notice {
    private static final Notice SHARED = new Notice("Java");
    private final String label;
    private Notice(String label) { this.label = label; }
    static Notice shared() { return SHARED; }
    static Notice fresh() { return new Notice("Java"); }
    String text() { return label; }
}
