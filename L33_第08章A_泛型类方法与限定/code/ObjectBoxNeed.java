public class ObjectBoxNeed {
    static class LooseBox { private Object value; void set(Object value) { this.value = value; } Object get() { return value; } }
    public static void main(String[] args) {
        LooseBox box = new LooseBox(); box.set("Java"); String title = (String) box.get(); System.out.println(title);
        box.set(20);
        try { String wrong = (String) box.get(); System.out.println(wrong); } catch (ClassCastException e) { System.out.println("read-failed"); }
    }
}
