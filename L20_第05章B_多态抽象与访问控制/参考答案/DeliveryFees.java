public class DeliveryFees {
    public static void main(String[] args) {
        Delivery[] items = {new WalkDelivery("步行"), new BikeDelivery("单车")};
        for (Delivery item : items) System.out.println(item.name() + ":" + item.fee());
        Delivery bike = new BikeDelivery("备用");
        if (bike instanceof BikeDelivery) System.out.println(((BikeDelivery) bike).capacity());
    }
}
abstract class Delivery {
    private final String name;
    Delivery(String name) { this.name = name; }
    public String name() { return name; }
    public abstract int fee();
}
class WalkDelivery extends Delivery {
    WalkDelivery(String name) { super(name); }
    @Override public int fee() { return 0; }
}
class BikeDelivery extends Delivery {
    BikeDelivery(String name) { super(name); }
    @Override public int fee() { return 2; }
    public int capacity() { return 3; }
}
