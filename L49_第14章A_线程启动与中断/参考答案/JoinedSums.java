public class JoinedSums {
    static long sum(int n) { if (n < 0) throw new IllegalArgumentException("negative"); long s = 0; for (int i = 1; i <= n; i++) s += i; return s; }
    public static void main(String[] args) throws InterruptedException {
        long[] results = new long[2]; Thread a = new Thread(() -> results[0] = sum(100)); Thread b = new Thread(() -> results[1] = sum(200));
        a.start(); b.start(); a.join(); b.join(); System.out.println(results[0]); System.out.println(results[1]); System.out.println(sum(0));
    }
}
