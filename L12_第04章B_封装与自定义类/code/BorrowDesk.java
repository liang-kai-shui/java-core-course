public class BorrowDesk {
    public static void main(String[] args) {
        Borrower reader = new Borrower("小林", 2);
        System.out.println(reader.summary());
        System.out.println(reader.borrow());
        System.out.println(reader.borrow());
        System.out.println(reader.borrow());
        System.out.println(reader.summary());
    }
}
class Borrower {
    private final String name;
    private final int limit;
    private int count;
    Borrower(String name, int limit) {
        this.name = name;
        this.limit = limit;
    }
    boolean borrow() {
        if (count == limit) return false;
        count++;
        return true;
    }
    String summary() { return name + ":" + count + "/" + limit; }
}
