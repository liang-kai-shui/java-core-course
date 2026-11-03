import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
public class ProxyRoute {
    public interface NoticeService { String send(String text); }
    public static class RealNotice implements NoticeService {
        @Override public String send(String text) { return "sent:" + text; }
    }
    static class Trace implements InvocationHandler {
        private final NoticeService target;
        Trace(NoticeService target) { this.target = target; }
        @Override public Object invoke(Object proxy, Method method, Object[] arguments) throws Throwable {
            if (method.getDeclaringClass() == Object.class) {
                if (method.getName().equals("equals")) return proxy == arguments[0];
                if (method.getName().equals("hashCode")) return System.identityHashCode(proxy);
                if (method.getName().equals("toString")) return "NoticeProxy";
            }
            System.out.println("before:" + method.getName());
            try {
                Object result = method.invoke(target, arguments);
                System.out.println("after:" + method.getName());
                return result;
            } catch (InvocationTargetException ex) { throw ex.getCause(); }
        }
    }
    public static void main(String[] args) {
        NoticeService proxy = (NoticeService) Proxy.newProxyInstance(NoticeService.class.getClassLoader(),
            new Class<?>[] {NoticeService.class}, new Trace(new RealNotice()));
        System.out.println(proxy.send("复习"));
        System.out.println(proxy.equals(proxy));
        System.out.println(proxy);
    }
}
