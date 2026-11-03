import java.util.*;
import java.util.concurrent.locks.*;
public class ReadWriteCatalog {
    static class Catalog {
        private final List<String> names = new ArrayList<>(); private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        void add(String name) { lock.writeLock().lock(); try { names.add(name); } finally { lock.writeLock().unlock(); } }
        List<String> snapshot() { lock.readLock().lock(); try { return new ArrayList<>(names); } finally { lock.readLock().unlock(); } }
    }
    public static void main(String[] args) throws InterruptedException {
        Catalog c = new Catalog(); c.add("数组"); c.snapshot().clear(); int[] sizes = new int[2];
        Thread a = new Thread(() -> sizes[0] = c.snapshot().size()), b = new Thread(() -> sizes[1] = c.snapshot().size());
        a.start(); b.start(); a.join(); b.join(); System.out.println(sizes[0] + ":" + sizes[1]);
    }
}
