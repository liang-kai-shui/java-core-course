import java.util.*;
public class EmptyStudyReport {
    static long completedMinutes(List<Integer> completed) { long sum = 0; for (int n : completed) { if (n < 0) throw new IllegalArgumentException("negative"); sum += n; } return sum; }
    public static void main(String[] args) { System.out.println(completedMinutes(Collections.<Integer>emptyList())); System.out.println(completedMinutes(Arrays.asList(30, 20))); }
}
