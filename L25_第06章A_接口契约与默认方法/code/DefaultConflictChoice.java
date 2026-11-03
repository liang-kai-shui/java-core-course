public class DefaultConflictChoice {
    public static void main(String[] args) {
        LeftLabel item = new LabelChoice();
        System.out.println(item.label());
    }
}
interface LeftLabel { default String label() { return "left"; } }
interface RightLabel { default String label() { return "right"; } }
class LabelChoice implements LeftLabel, RightLabel {
    @Override public String label() { return LeftLabel.super.label() + "/" + RightLabel.super.label(); }
}
