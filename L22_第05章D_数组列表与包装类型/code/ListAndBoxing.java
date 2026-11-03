import java.util.ArrayList;
public class ListAndBoxing {
    public static void main(String[] args) {
        ArrayList<Integer> scores = new ArrayList<>(100);
        System.out.println(scores.size());
        scores.add(10);
        scores.add(20);
        scores.add(1, 15);
        scores.set(0, 12);
        System.out.println(scores);
        scores.remove(Integer.valueOf(15));
        System.out.println(scores);
        int sum = 0;
        for (int score : scores) sum += score;
        System.out.println("sum=" + sum);
        System.out.println(Integer.valueOf(1000).equals(Integer.valueOf(1000)));
    }
}
