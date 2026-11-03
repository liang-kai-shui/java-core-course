public class OrderedPairLocks {
    static class Cell { final int id; int visits; Cell(int id) { this.id = id; } }
    static void visit(Cell a, Cell b) {
        if (a == b) { synchronized (a) { a.visits++; } return; }
        if (a.id == b.id) throw new IllegalArgumentException("ids must be unique");
        Cell first = a.id < b.id ? a : b, second = first == a ? b : a;
        synchronized (first) { synchronized (second) { a.visits++; b.visits++; } }
    }
    public static void main(String[] args) throws InterruptedException {
        Cell a = new Cell(1), b = new Cell(2); Thread x = new Thread(() -> visit(a, b)), y = new Thread(() -> visit(b, a));
        x.start(); y.start(); x.join(); y.join(); System.out.println(a.visits + ":" + b.visits);
    }
}
