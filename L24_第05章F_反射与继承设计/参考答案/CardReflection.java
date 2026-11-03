import java.lang.reflect.Method;
public class CardReflection {
    public static void main(String[] args) {
        try {
            Class<?> type = SampleCard.class;
            Object card = type.getConstructor(String.class).newInstance("林");
            Method method = type.getMethod("label");
            System.out.println(method.invoke(card));
        } catch (ReflectiveOperationException ex) {
            System.out.println("失败:" + ex.getClass().getSimpleName());
        }
    }
}
class SampleCard {
    private final String name;
    public SampleCard(String name) { this.name = name; }
    public String label() { return "卡:" + name; }
}
