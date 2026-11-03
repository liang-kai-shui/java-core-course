import java.util.LinkedHashMap;
import java.util.Map;
public class MapAccessOrder {
    public static void main(String[] args) {
        Map<String, Integer> insertion = new LinkedHashMap<>();
        Map<String, Integer> access = new LinkedHashMap<>(16, 0.75f, true);
        insertion.put("A", 1); insertion.put("B", 2);
        access.put("A", 1); access.put("B", 2);
        insertion.get("A"); access.get("A");
        System.out.println(insertion.keySet());
        System.out.println(access.keySet());
    }
}
