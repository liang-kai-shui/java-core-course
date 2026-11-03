import java.util.*;
import java.util.prefs.AbstractPreferences;
import java.util.prefs.Preferences;
public class MemoryPreferenceTree {
    static class MemoryNode extends AbstractPreferences {
        private final Map<String, String> values = new HashMap<>();
        private final Map<String, MemoryNode> children = new HashMap<>();
        MemoryNode(AbstractPreferences parent, String name) { super(parent, name); }
        protected void putSpi(String key, String value) { values.put(key, value); }
        protected String getSpi(String key) { return values.get(key); }
        protected void removeSpi(String key) { values.remove(key); }
        protected void removeNodeSpi() { values.clear(); }
        protected String[] keysSpi() { return values.keySet().toArray(new String[0]); }
        protected String[] childrenNamesSpi() { return children.keySet().toArray(new String[0]); }
        protected AbstractPreferences childSpi(String name) { return children.computeIfAbsent(name, n -> new MemoryNode(this, n)); }
        protected void syncSpi() { }
        protected void flushSpi() { }
    }
    public static void main(String[] args) {
        Preferences root = new MemoryNode(null, ""); Preferences node = root.node("/com/example/course/window");
        node.putInt("width", 420); System.out.println(node.absolutePath()); System.out.println(node.getInt("width", 300));
        node.put("width", "bad"); System.out.println(node.getInt("width", 300));
    }
}
