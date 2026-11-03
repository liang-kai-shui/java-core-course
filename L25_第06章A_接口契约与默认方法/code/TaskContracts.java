public class TaskContracts {
    public static void main(String[] args) {
        NamedTask task = new BookTask("泛型");
        System.out.println(task.display());
        System.out.println(NamedTask.category());
    }
}
interface NamedTask {
    String name();
    default String display() { return "task:" + name(); }
    static String category() { return "学习"; }
}
class BookTask implements NamedTask {
    private final String title;
    BookTask(String title) { this.title = title; }
    @Override public String name() { return title; }
}
