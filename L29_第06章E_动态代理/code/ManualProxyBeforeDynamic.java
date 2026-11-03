public class ManualProxyBeforeDynamic {
    public static void main(String[] args) {
        MessagePort view = new LoggedMessage(new RealMessage());
        System.out.println(view.send("数组"));
    }
}
interface MessagePort { String send(String text); }
class RealMessage implements MessagePort {
    public String send(String text) { return "sent:" + text; }
}
class LoggedMessage implements MessagePort {
    private final MessagePort target;
    LoggedMessage(MessagePort target) { this.target = target; }
    public String send(String text) {
        System.out.println("before");
        String result = target.send(text);
        System.out.println("after");
        return result;
    }
}
