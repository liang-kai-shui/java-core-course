public class ExamContract {
    public static void main(String[] args) {
        for (int n : new int[] {59, 60, 100}) {
            Scored attempt = new ExamAttempt(n);
            System.out.println(attempt.score() + ":" + attempt.passed());
        }
    }
}
interface Scored {
    int score();
    default boolean passed() { return score() >= 60; }
}
class ExamAttempt implements Scored {
    private final int value;
    ExamAttempt(int value) { this.value = value; }
    @Override public int score() { return value; }
}
