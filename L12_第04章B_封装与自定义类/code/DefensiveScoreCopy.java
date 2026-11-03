public class DefensiveScoreCopy {
    public static void main(String[] args) {
        int[] input = {70, 80};
        ScoreSnapshot scores = new ScoreSnapshot(input);
        input[0] = 1;
        int[] view = scores.snapshot();
        view[0] = 2;
        System.out.println("input=" + input[0]);
        System.out.println("view=" + view[0]);
        System.out.println("inside=" + scores.first());
    }
}
class ScoreSnapshot {
    private final int[] values;
    ScoreSnapshot(int[] values) { this.values = values.clone(); }
    int[] snapshot() { return values.clone(); }
    int first() { return values[0]; }
}
