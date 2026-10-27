import java.util.Locale;
import java.util.Scanner;
public class InputDemo {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in).useLocale(Locale.US);
        System.out.println("年龄、姓名、单价、数量，各一行：");
        int age = in.nextInt();
        in.nextLine();
        String name = in.nextLine();
        double price = in.nextDouble();
        int count = in.nextInt();
        System.out.println("name=" + name + ", age=" + age);
        System.out.printf(Locale.US, "total=%.2f%n", price * count);
        // 此程序随main结束；不提前关闭后续可能还要读取的System.in。
    }
}
