public class ReturnDesk {
    public static void main(String[] args) {
        LoanCounter c = new LoanCounter(2);
        System.out.println(c.returnOne() + ":" + c.getCount());
        System.out.println(c.borrow() + ":" + c.getCount());
        System.out.println(c.borrow() + ":" + c.getCount());
        System.out.println(c.borrow() + ":" + c.getCount());
        System.out.println(c.returnOne() + ":" + c.getCount());
        System.out.println(new LoanCounter(-1).borrow());
        System.out.println(new LoanCounter(0).borrow());
    }
}
class LoanCounter {
    private final int limit;
    private int count;
    LoanCounter(int limit) { this.limit = limit < 0 ? 0 : limit; }
    boolean borrow() { if (count >= limit) return false; count++; return true; }
    boolean returnOne() { if (count == 0) return false; count--; return true; }
    int getCount() { return count; }
}
