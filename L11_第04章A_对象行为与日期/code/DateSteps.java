import java.time.LocalDate;
public class DateSteps {
    public static void main(String[] args) {
        LocalDate start = LocalDate.of(2024, 12, 31);
        start.plusDays(1);
        LocalDate next = start.plusDays(1);
        System.out.println(start);
        System.out.println(next);
        System.out.println("year=" + next.getYear() + ", month=" + next.getMonthValue() + ", day=" + next.getDayOfMonth());
        LocalDate cursor = start;
        for (int i = 0; i < 3; i++) {
            System.out.print(cursor + (i == 2 ? "\n" : " "));
            cursor = cursor.plusDays(1);
        }
    }
}
