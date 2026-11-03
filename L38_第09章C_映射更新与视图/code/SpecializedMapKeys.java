import java.util.EnumMap;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;
public class SpecializedMapKeys {
    enum Stage { TODO, DONE }
    public static void main(String[] args) {
        String a = new String("same");
        String b = new String("same");
        Map<String, Integer> values = new HashMap<>();
        Map<String, Integer> identities = new IdentityHashMap<>();
        values.put(a, 1); values.put(b, 2);
        identities.put(a, 1); identities.put(b, 2);
        System.out.println(values.size()); System.out.println(identities.size());
        Map<Stage, Integer> counts = new EnumMap<>(Stage.class);
        counts.put(Stage.DONE, 1); counts.put(Stage.TODO, 2);
        System.out.println(counts);
    }
}
