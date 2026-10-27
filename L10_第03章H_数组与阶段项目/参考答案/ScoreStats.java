import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;
public class ScoreStats {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        if (n < 0 || n > 100) {
            System.out.println("invalid count");
            return;
        }
        if (n == 0) {
            System.out.println("no data");
            return;
        }
        int[] scores = new int[n];
        for (int i = 0; i < n; i++) {
            scores[i] = in.nextInt();
        }
        int sum = 0;
        int maximum = scores[0];
        int minimum = scores[0];
        int passed = 0;
        for (int score : scores) {
            sum += score;
            if (score > maximum) { maximum = score; }
            if (score < minimum) { minimum = score; }
            if (score >= 60) { passed++; }
        }
        int[] sorted = Arrays.copyOf(scores, scores.length);
        Arrays.sort(sorted);
        System.out.println("original=" + Arrays.toString(scores));
        System.out.println("sum=" + sum);
        System.out.printf(Locale.US, "average=%.2f%n", sum / (double) n);
        System.out.println("max=" + maximum);
        System.out.println("min=" + minimum);
        System.out.println("passed=" + passed);
        System.out.println("sorted=" + Arrays.toString(sorted));
    }
}
