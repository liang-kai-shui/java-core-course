public class EqualityEntryTrap {
    public static void main(String[] args) {
        WrongKey a = new WrongKey(3);
        WrongKey b = new WrongKey(3);
        Object oa = a;
        Object ob = b;
        System.out.println(a.equals(b));
        System.out.println(oa.equals(ob));
    }
}
class WrongKey {
    private final int id;
    WrongKey(int id) { this.id = id; }
    public boolean equals(WrongKey other) { return other != null && id == other.id; }
}
