import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
public class ConcurrentViewsAndReduction {
    public static void main(String[] args) {
        ConcurrentHashMap<String, Long> map = new ConcurrentHashMap<>(); map.put("A", 2L); map.put("B", 3L);
        System.out.println(map.reduceValues(Long.MAX_VALUE, Long::sum)); String found = map.search(Long.MAX_VALUE, (k, v) -> v > 2 ? k : null); System.out.println(found);
        map.clear(); System.out.println(map.reduceValues(Long.MAX_VALUE, Long::sum)); System.out.println(map.reduceValuesToLong(Long.MAX_VALUE, Long::longValue, 0L, Long::sum));
        map.put("old", 5L); Set<String> keys = map.keySet(1L); keys.add("new"); keys.remove("old"); System.out.println(map.get("new") + ":" + map.containsKey("old"));
        Set<String> independent = ConcurrentHashMap.newKeySet(); independent.add("X"); System.out.println(independent.contains("X"));
    }
}
