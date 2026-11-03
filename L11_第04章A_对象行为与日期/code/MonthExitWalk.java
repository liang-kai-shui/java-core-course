import java.time.LocalDate;
public class MonthExitWalk {
    public static void main(String[] args) {
        LocalDate cursor = LocalDate.of(2025, 4, 27);
        int month = cursor.getMonthValue();
        int count = 0;
        while (cursor.getMonthValue() == month) {
            System.out.println(cursor + " " + cursor.getDayOfWeek().getValue());
            count++;
            cursor = cursor.plusDays(1);
        }
        System.out.println("count=" + count + ", next=" + cursor);
    }
}
