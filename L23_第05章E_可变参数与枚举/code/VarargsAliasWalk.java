import java.util.Arrays;
public class VarargsAliasWalk {
    static void raiseFirst(int... values) {
        if (values.length > 0) values[0]++;
    }
    public static void main(String[] args) {
        int[] data = {3, 4};
        raiseFirst(data);
        raiseFirst();
        raiseFirst(7, 8);
        System.out.println(Arrays.toString(data));
    }
}
