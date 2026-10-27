public class VariableDemo {
    public static void main(String[] args) {
        int count = 3;
        double price = 12.5;
        final int DAYS = 7;
        System.out.println("count=" + count);
        count = count + 1;
        System.out.println("updated=" + count);
        System.out.println("total=" + count * price);
        System.out.println("days=" + DAYS);
        int maximum = Integer.MAX_VALUE;
        System.out.println("overflow=" + (maximum + 1));
    }
}
