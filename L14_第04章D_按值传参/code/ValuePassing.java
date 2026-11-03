public class ValuePassing {
    public static void main(String[] args) {
        int n = 3;
        Box a = new Box(5);
        Box b = new Box(20);
        changeNumber(n);
        changeState(a);
        replace(a);
        System.out.println(n + ":" + a.value);
        swap(a, b);
        System.out.println(a.value + ":" + b.value);
    }
    static void changeNumber(int value) { value = 99; }
    static void changeState(Box box) { box.value += 3; }
    static void replace(Box box) { box = new Box(99); }
    static void swap(Box left, Box right) {
        Box temp = left;
        left = right;
        right = temp;
    }
}
class Box {
    int value;
    Box(int value) { this.value = value; }
}
