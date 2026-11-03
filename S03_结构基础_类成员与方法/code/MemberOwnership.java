public class MemberOwnership {
    public static void main(String[] args) {
        MemberState a = new MemberState("Lin", 2);
        MemberState b = new MemberState("Mei", 0);
        a.add(3);
        System.out.println(a.describe());
        System.out.println(b.describe());
        System.out.println("created=" + MemberState.created());
    }
}
class MemberState {
    private final String owner;
    private int points;
    private static int created;
    MemberState(String owner, int points) { this.owner = owner; this.points = points; created++; }
    void add(int amount) { points += amount; }
    String describe() { return owner + ":" + points; }
    static int created() { return created; }
}
