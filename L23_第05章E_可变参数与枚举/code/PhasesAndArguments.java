public class PhasesAndArguments {
    static long sum(int... minutes) {
        long total = 0;
        for (int value : minutes) total += value;
        return total;
    }
    public static void main(String[] args) {
        System.out.println(sum());
        System.out.println(sum(3, 4));
        for (Phase phase : Phase.values()) System.out.println(phase.name() + ":" + phase.label());
        System.out.println(Phase.valueOf("DOING") == Phase.DOING);
    }
}
enum Phase {
    TODO("待办"), DOING("进行中"), DONE("完成");
    private final String label;
    Phase(String label) { this.label = label; }
    String label() { return label; }
}
