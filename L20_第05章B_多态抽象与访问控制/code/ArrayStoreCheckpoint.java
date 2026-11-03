public class ArrayStoreCheckpoint {
    public static void main(String[] args) {
        Integer[] numbers = new Integer[1];
        Object[] view = numbers;
        System.out.println("before");
        view[0] = Integer.valueOf(1);
        System.out.println("stored=" + numbers[0]);
    }
}
