import java.util.Arrays;
import java.util.Comparator;
public class CallbackSorting {
    public static void main(String[] args) throws CloneNotSupportedException {
        Hook hook = new DonePrinter();
        hook.done("数组");
        String[] words = {"ccc", "a", "bb"};
        Arrays.sort(words, new LengthPolicy());
        System.out.println(Arrays.toString(words));
        PracticeRecord a = new PracticeRecord(new int[] {1, 2});
        PracticeRecord b = a.clone();
        b.setFirst(9);
        System.out.println(a);
        System.out.println(b);
    }
}
interface Hook { void done(String title); }
class DonePrinter implements Hook {
    @Override public void done(String title) { System.out.println("完成:" + title); }
}
class LengthPolicy implements Comparator<String> {
    @Override public int compare(String a, String b) { return Integer.compare(a.length(), b.length()); }
}
final class PracticeRecord implements Cloneable {
    private int[] values;
    PracticeRecord(int[] values) { this.values = values.clone(); }
    void setFirst(int n) { values[0] = n; }
    @Override public PracticeRecord clone() throws CloneNotSupportedException {
        PracticeRecord copy = (PracticeRecord) super.clone();
        copy.values = values.clone();
        return copy;
    }
    @Override public String toString() { return Arrays.toString(values); }
}
