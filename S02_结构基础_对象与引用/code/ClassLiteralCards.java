public class ClassLiteralCards {
    public static void main(String[] args) {
        TypeCard first = new TypeCard("Lin");
        TypeCard second = new TypeCard("Lin");
        System.out.println("two-objects=" + (first != second));
        System.out.println("same-type=" + (first.getClass() == second.getClass()));
        System.out.println("same-description=" + (first.getClass() == TypeCard.class));
    }
}
class TypeCard {
    private final String name;
    TypeCard(String name) { this.name = name; }
    String getName() { return name; }
}
