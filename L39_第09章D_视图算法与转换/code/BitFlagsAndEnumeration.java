import java.util.Arrays;
import java.util.BitSet;
import java.util.Collections;
import java.util.Enumeration;
public class BitFlagsAndEnumeration {
    public static void main(String[] args) {
        BitSet done = new BitSet(128);
        done.set(2); done.set(9);
        System.out.println("length=" + done.length()); System.out.println("count=" + done.cardinality());
        done.clear(9);
        System.out.println("length=" + done.length()); System.out.println("count=" + done.cardinality());
        Enumeration<String> oldAccess = Collections.enumeration(Arrays.asList("A", "B"));
        System.out.println(Collections.list(oldAccess));
    }
}
