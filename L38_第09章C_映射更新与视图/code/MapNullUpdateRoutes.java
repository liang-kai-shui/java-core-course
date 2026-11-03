import java.util.LinkedHashMap;
import java.util.Map;
public class MapNullUpdateRoutes {
    public static void main(String[] args) {
        Map<String, Integer> counts = new LinkedHashMap<>();
        for (String word : new String[]{"read", "code", "read"}) counts.merge(word, 1, Integer::sum);
        System.out.println(counts);
        counts.computeIfAbsent("missing", key -> null);
        System.out.println(counts.containsKey("missing"));
        counts.put("nullable", null);
        counts.computeIfAbsent("nullable", key -> null);
        System.out.println(counts.containsKey("nullable"));
        counts.replaceAll((key, value) -> null);
        System.out.println(counts.containsKey("read"));
    }
}
