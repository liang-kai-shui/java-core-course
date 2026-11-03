public class ColorPrice {
    public static void main(String[] args) {
        System.out.println(new ColorJob(4, 3).cost());
        System.out.println(new ColorJob(0, 3).cost());
        System.out.println(new ColorJob(1, 0).cost());
    }
}
class PrintJob {
    private final int pages;
    PrintJob(int pages) { this.pages = pages; }
    public int cost() { return pages * 2; }
}
class ColorJob extends PrintJob {
    private final int extra;
    ColorJob(int pages, int extra) { super(pages); this.extra = extra; }
    @Override public int cost() { return super.cost() + extra; }
}
