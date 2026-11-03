import java.util.*;
public class LocalTaskNotes {
    static final ThreadLocal<List<String>> notes = ThreadLocal.withInitial(ArrayList::new);
    static void work(String name, String[] result, int index) {
        try { notes.get().add(name); result[index] = notes.get().get(0); } finally { notes.remove(); }
    }
    public static void main(String[] args) throws InterruptedException {
        String[] result = new String[2]; Thread a = new Thread(() -> work("A", result, 0)), b = new Thread(() -> work("B", result, 1));
        a.start(); b.start(); a.join(); b.join(); System.out.println(result[0]); System.out.println(result[1]);
        try { System.out.println("main-size=" + notes.get().size()); } finally { notes.remove(); }
    }
}
