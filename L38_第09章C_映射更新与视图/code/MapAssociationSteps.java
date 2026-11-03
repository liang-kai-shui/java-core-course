import java.util.LinkedHashMap;
import java.util.Map;
public class MapAssociationSteps {
    public static void main(String[] args) {
        Map<String, Integer> scores = new LinkedHashMap<>();
        System.out.println(scores.put("Lin", 10));
        System.out.println(scores.put("Lin", 20));
        System.out.println(scores.size());
        System.out.println(scores.get("Lin"));
        System.out.println(scores.getOrDefault("missing", 0));
        scores.put("empty", null);
        System.out.println(scores.get("empty"));
        System.out.println(scores.containsKey("empty"));
    }
}
