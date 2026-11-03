public class GenericParcelDemo {
    public static void main(String[] args) {
        Parcel<String> title = new Parcel<>("Java");
        Parcel<Integer> minutes = new Parcel<>(20);
        System.out.println(title.get());
        System.out.println(minutes.get());
    }
}
class Parcel<T> {
    private final T value;
    Parcel(T value) { this.value = value; }
    T get() { return value; }
}
