import java.util.ArrayList;
import java.util.Arrays;
public class RemoveAndUnboxRoutes {
    public static void main(String[] args) {
        ArrayList<Integer> byIndex = new ArrayList<>(Arrays.asList(9, 1, 7));
        ArrayList<Integer> byValue = new ArrayList<>(Arrays.asList(9, 1, 7));
        byIndex.remove(1);
        byValue.remove(Integer.valueOf(1));
        System.out.println(byIndex);
        System.out.println(byValue);
        Integer boxed = 3;
        int plain = boxed;
        System.out.println(plain);
    }
}
