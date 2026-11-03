public class CallbackRoute {
    public static void main(String[] args) {
        Flow.run("阅读", new PrintingHook());
    }
}
interface CompleteHook { void completed(String title); }
class PrintingHook implements CompleteHook {
    @Override public void completed(String title) { System.out.println("record:" + title); }
}
class Flow {
    static void run(String title, CompleteHook hook) {
        System.out.println("prepare:" + title);
        hook.completed(title);
        System.out.println("finish:" + title);
    }
}
