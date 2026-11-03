public class CallbackStateOwner {
    public static void main(String[] args) {
        CounterHook hook = new CounterHook();
        System.out.println("before=" + hook.count());
        TwiceFlow.run(hook);
        System.out.println("after=" + hook.count());
    }
}
interface CountHook { void done(); }
class CounterHook implements CountHook {
    private int count;
    public void done() { count++; }
    int count() { return count; }
}
class TwiceFlow {
    static void run(CountHook hook) { hook.done(); hook.done(); }
}
