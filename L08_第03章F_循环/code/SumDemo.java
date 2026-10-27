public class SumDemo {
    public static void main(String[] args) {
        int n = args.length == 0 ? 5 : Integer.parseInt(args[0]);
        int sum = 0;
        int i = 1;
        while (i <= n) {
            sum += i;
            i++;
        }
        System.out.println("while=" + sum);
        int total = 0;
        for (int j = 1; j <= n; j++) {
            total += j;
        }
        System.out.println("for=" + total);
    }
}
