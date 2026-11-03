public class ResetComparison {
    public static void main(String[] args) {
        Cell original = new Cell(7);
        Cell copy = resetCopy(original);
        System.out.println(original.value + ":" + copy.value);
        Cell alias = original;
        Cell separate = new Cell(7);
        resetInPlace(original);
        System.out.println(original.value + ":" + copy.value);
        System.out.println(alias.value + ":" + separate.value);
    }
    static Cell resetCopy(Cell source) { return new Cell(0); }
    static void resetInPlace(Cell source) { source.value = 0; }
}
class Cell {
    int value;
    Cell(int value) { this.value = value; }
}
