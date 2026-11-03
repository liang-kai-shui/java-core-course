import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
public class SnapshotAndSynchronizedWalk {
    public static void main(String[] args) {
        CopyOnWriteArrayList<String> copy = new CopyOnWriteArrayList<>(); copy.add("A"); Iterator<String> old = copy.iterator(); copy.add("B");
        while (old.hasNext()) System.out.println(old.next());
        try { old.remove(); } catch (UnsupportedOperationException expected) { System.out.println("snapshot-remove-rejected"); }
        System.out.println(copy.size()); List<String> safe = Collections.synchronizedList(new ArrayList<String>()); safe.add("A"); safe.add("B");
        synchronized (safe) { int count = 0; for (String name : safe) if (!name.isEmpty()) count++; System.out.println(count); }
    }
}
