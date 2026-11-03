public class FieldAndLocalDefaults {
    public static void main(String[] args) {
        DefaultState state = new DefaultState();
        int local;
        System.out.println(state.number);
        System.out.println(state.active);
        System.out.println(state.label);
    }
}
class DefaultState {
    int number;
    boolean active;
    String label;
}
