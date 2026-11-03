import java.util.ArrayList;
public class CapturedReferenceState {
    public static void main(String[] args) {
        ArrayList<String> names = new ArrayList<>();
        Runnable show = () -> System.out.println(names.size());
        names.add("林");
        show.run();
        names.clear();
        show.run();
    }
}
