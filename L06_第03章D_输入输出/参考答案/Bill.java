import java.util.Locale;
import java.util.Scanner;
public class Bill {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in).useLocale(Locale.US);
        String name = in.nextLine();
        double price = in.nextDouble();
        int count = in.nextInt();
        System.out.println("name=" + name);
        System.out.printf(Locale.US, "total=%.2f%n", price * count);
    }
}
