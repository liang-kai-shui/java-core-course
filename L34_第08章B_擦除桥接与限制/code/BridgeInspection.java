import java.lang.reflect.Method;
public class BridgeInspection {
    public static void main(String[] args) {
        Slot<String> base = new StringSlot();
        System.out.println(base.get());
        int normal = 0, bridge = 0;
        for (Method method : StringSlot.class.getDeclaredMethods()) {
            if (method.isBridge()) bridge++; else normal++;
        }
        System.out.println("normal=" + normal + " bridge=" + bridge);
    }
}
class Slot<T> { T get() { return null; } }
class StringSlot extends Slot<String> { @Override String get() { return "child"; } }
