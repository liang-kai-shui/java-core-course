import java.util.Scanner;
public class Ticket {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int age = in.nextInt();
        if (age < 0 || age > 120) {
            System.out.println("invalid");
        } else if (age <= 5) {
            System.out.println("free");
        } else if (age <= 17) {
            System.out.println("half");
        } else if (age <= 59) {
            System.out.println("full");
        } else {
            System.out.println("senior");
        }
    }
}
