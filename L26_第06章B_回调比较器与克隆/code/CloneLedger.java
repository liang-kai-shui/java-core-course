import java.util.Arrays;
public class CloneLedger {
    public static void main(String[] args) throws CloneNotSupportedException {
        Notes original = new Notes(new int[] {1, 2});
        Notes copy = original.clone();
        copy.setFirst(9);
        System.out.println(original);
        System.out.println(copy);
    }
}
final class Notes implements Cloneable {
    private int[] marks;
    Notes(int[] marks) { this.marks = marks.clone(); }
    void setFirst(int value) { marks[0] = value; }
    @Override public Notes clone() throws CloneNotSupportedException {
        Notes copy = (Notes) super.clone();
        copy.marks = marks.clone();
        return copy;
    }
    @Override public String toString() { return Arrays.toString(marks); }
}
