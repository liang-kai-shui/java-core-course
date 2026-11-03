public class GoalAliasWalk {
    public static void main(String[] args) {
        SmallGoal one = new SmallGoal();
        SmallGoal[] shared = {one, one};
        shared[0].record(3);
        System.out.println(shared[0].done() + ":" + shared[1].done());
        SmallGoal[] independent = {new SmallGoal(), new SmallGoal()};
        independent[0].record(3);
        System.out.println(independent[0].done() + ":" + independent[1].done());
    }
}
class SmallGoal {
    private int done;
    boolean record(int amount) {
        if (amount <= 0 || done == 10) return false;
        done += Math.min(amount, 10 - done);
        return true;
    }
    int done() { return done; }
}
