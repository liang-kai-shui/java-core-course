import java.awt.EventQueue;
public class EdtCallSequence {
    public static void main(String[] args) throws Exception {
        System.out.println("before");
        EventQueue.invokeAndWait(() -> System.out.println("edt=" + EventQueue.isDispatchThread()));
        System.out.println("after");
    }
}
