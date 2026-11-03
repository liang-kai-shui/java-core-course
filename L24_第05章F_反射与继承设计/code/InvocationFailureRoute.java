import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
public class InvocationFailureRoute {
    public static void main(String[] args) {
        try {
            Method method = CheckedLabel.class.getMethod("load", String.class);
            System.out.println(method.invoke(new CheckedLabel(), ""));
        } catch (InvocationTargetException ex) {
            System.out.println("cause=" + ex.getCause().getClass().getSimpleName());
        } catch (ReflectiveOperationException ex) {
            System.out.println("lookup=" + ex.getClass().getSimpleName());
        }
    }
}
class CheckedLabel {
    public String load(String text) {
        if (text.isEmpty()) throw new IllegalArgumentException("empty");
        return "book:" + text;
    }
}
