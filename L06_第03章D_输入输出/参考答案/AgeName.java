import java.util.Scanner;
public class AgeName {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int age = in.nextInt();
        in.nextLine();
        String name = in.nextLine();
        System.out.println(name + ":" + age);
    }
}
