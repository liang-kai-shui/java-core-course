public class ShortCircuitDemo {
    public static void main(String[] args) {
        int divisor = 0;
        boolean safe = divisor != 0 && 10 / divisor > 1;
        System.out.println("safe=" + safe);
    }
}
