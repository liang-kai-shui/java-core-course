import java.time.LocalDate;
public class FiveDates {
    public static void main(String[] args) {
        LocalDate date = LocalDate.of(2025, 1, 30);
        for (int i = 0; i < 5; i++) {
            System.out.println(date + " " + date.getDayOfWeek().getValue());
            date = date.plusDays(1);
        }
    }
}
