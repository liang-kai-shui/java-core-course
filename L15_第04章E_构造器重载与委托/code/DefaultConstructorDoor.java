public class DefaultConstructorDoor {
    public static void main(String[] args) {
        System.out.println(new PlainDoor().title);
        System.out.println(new NamedDoor("数组").title);
    }
}
class PlainDoor {
    String title = "默认";
}
class NamedDoor {
    String title = "默认";
    NamedDoor(String title) { this.title = title; }
}
