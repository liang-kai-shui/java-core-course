public class CountdownDemo {
    public static void main(String[] args) {
        int left = 0;
        do {
            System.out.println("left=" + left);
            left--;
        } while (left > 0);
    }
}
