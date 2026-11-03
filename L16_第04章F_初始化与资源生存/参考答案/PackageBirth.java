public class PackageBirth {
    public static void main(String[] args) {
        System.out.println(new PackageItem().summary());
        System.out.println(new PackageItem("教材").summary());
        System.out.println("next=" + PackageItem.nextNumber());
    }
}
class PackageItem {
    private static int next = 10;
    private final int id = next++;
    private final String title;
    PackageItem() { this("默认"); }
    PackageItem(String title) { this.title = title; }
    static int nextNumber() { return next; }
    String summary() { return id + ":" + title; }
}
