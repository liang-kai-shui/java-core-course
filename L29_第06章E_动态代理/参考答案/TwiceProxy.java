import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
public class TwiceProxy {
    public interface ValueService { int twice(int value); }
    public static class RealValue implements ValueService {
        @Override public int twice(int value) { return value * 2; }
    }
    static class Handler implements InvocationHandler {
        private final ValueService target;
        Handler(ValueService target) { this.target = target; }
        @Override public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (method.getDeclaringClass() == Object.class) {
                if (method.getName().equals("equals")) return proxy == args[0];
                if (method.getName().equals("hashCode")) return System.identityHashCode(proxy);
                if (method.getName().equals("toString")) return "ValueProxy";
            }
            System.out.println("call:" + method.getName());
            try { return method.invoke(target, args); }
            catch (InvocationTargetException ex) { throw ex.getCause(); }
        }
    }
    public static void main(String[] args) {
        ValueService service = (ValueService) Proxy.newProxyInstance(ValueService.class.getClassLoader(),
            new Class<?>[] {ValueService.class}, new Handler(new RealValue()));
        for (int n : new int[] {0, 3, -2}) System.out.println(service.twice(n));
        System.out.println(Proxy.isProxyClass(service.getClass()));
    }
}
