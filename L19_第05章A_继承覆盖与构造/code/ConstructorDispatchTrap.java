public class ConstructorDispatchTrap {
    public static void main(String[] args) {
        ChildProbe probe = new ChildProbe();
        System.out.println("after=" + probe.read());
    }
}
class ParentProbe {
    ParentProbe() { System.out.println("during=" + read()); }
    int read() { return -1; }
}
class ChildProbe extends ParentProbe {
    private int bonus = 7;
    @Override int read() { return bonus; }
}
