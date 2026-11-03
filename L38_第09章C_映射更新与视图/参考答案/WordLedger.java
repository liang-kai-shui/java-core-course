import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
public class WordLedger {
    static Map<String, Integer> count(String[] words) {
        Map<String, Integer> counts = new LinkedHashMap<>();
        for (String word : words) counts.merge(word, 1, Integer::sum);
        return counts;
    }
    public static void main(String[] args) {
        Map<String, Integer> counts = count(new String[]{"read", "code", "read", "test"});
        for (Map.Entry<String, Integer> entry : counts.entrySet()) System.out.println(entry.getKey() + "=" + entry.getValue());
        Set<String> keys = counts.keySet(); List<String> snapshot = new ArrayList<>(keys);
        keys.remove("code"); System.out.println(counts); System.out.println(snapshot);
        System.out.println(count(new String[0]));
        Map<String, Integer> nullable = new LinkedHashMap<>();
        nullable.computeIfAbsent("missing", k -> null); System.out.println(nullable.containsKey("missing"));
        nullable.put("x", 1); nullable.replaceAll((k, v) -> null); System.out.println(nullable.containsKey("x"));
    }
}
