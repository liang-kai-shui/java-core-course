public class ArithmeticDemo {
    public static void main(String[] args) {
        System.out.println(7 / 2);
        System.out.println(7 / 2.0);
        System.out.println((double) (7 / 2));
        System.out.println((int) 3.9);
        int count = 1_000_000;
        long wrong = count * count;
        long right = (long) count * count;
        System.out.println("wrong=" + wrong);
        System.out.println("right=" + right);
        System.out.println(0.1 + 0.2);
    }
}
