import java.util.ArrayList;
import java.util.List;
public class VolatilePublishOnce {
    static class State { int answer; volatile boolean ready; }
    public static void main(String[] args) throws InterruptedException {
        State state = new State(); Thread writer = new Thread(() -> { state.answer = 42; state.ready = true; }); writer.start();
        while (!state.ready) Thread.yield(); System.out.println(state.answer); writer.join();
        final List<String> names = new ArrayList<>(); names.add("数组"); System.out.println("size=" + names.size());
    }
}
