public class ReturnThroughFinally {
    static int calculate() {
        int value = 1;
        try {
            return value;
        } finally {
            value = 2;
            System.out.println("finally=" + value);
        }
    }
    public static void main(String[] args) {
        System.out.println("returned=" + calculate());
    }
}
