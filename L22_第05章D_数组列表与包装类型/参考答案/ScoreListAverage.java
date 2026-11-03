import java.util.ArrayList;
public class ScoreListAverage {
    static String average(ArrayList<Integer> scores) {
        if (scores.isEmpty()) return "无成绩";
        long sum = 0;
        for (int score : scores) sum += score;
        return Double.toString((double) sum / scores.size());
    }
    public static void main(String[] args) {
        ArrayList<Integer> scores = new ArrayList<>();
        scores.add(60); scores.add(80); scores.add(100);
        scores.remove(0);
        System.out.println(average(scores));
        ArrayList<Integer> alias = scores;
        alias.clear();
        System.out.println(average(scores));
        scores.add(100);
        System.out.println(average(scores));
    }
}
