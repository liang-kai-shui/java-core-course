import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
public class ReflectionWorkshop {
    public static void main(String[] args) {
        try {
            Class<?> type = Class.forName("ReadingNote");
            System.out.println(type == ReadingNote.class);
            Constructor<?> constructor = type.getConstructor(String.class);
            Object note = constructor.newInstance("初读");
            System.out.println(note.getClass().getSimpleName());
            Field field = type.getDeclaredField("title");
            System.out.println(Modifier.toString(field.getModifiers()));
            field.setAccessible(true);
            field.set(note, "复习");
            Method method = type.getMethod("label", String.class);
            System.out.println(method.invoke(note, "!"));
        } catch (ReflectiveOperationException ex) {
            System.out.println("reflection-failed:" + ex.getClass().getSimpleName());
        }
    }
}
class ReadingNote {
    private String title;
    public ReadingNote(String title) { this.title = title; }
    public String label(String suffix) { return title + suffix; }
}
