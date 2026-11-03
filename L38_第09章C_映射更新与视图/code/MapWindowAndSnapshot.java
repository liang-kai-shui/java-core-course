import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
public class MapWindowAndSnapshot {
    public static void main(String[] args) {
        Map<String, Integer> map = new LinkedHashMap<>();
        map.put("A", 1); map.put("B", 2);
        Set<String> keys = map.keySet();
        List<String> snapshot = new ArrayList<>(keys);
        keys.remove("A");
        System.out.println(map);
        System.out.println(snapshot);
    }
}
