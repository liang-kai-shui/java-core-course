public class LampBoard {
    public static void main(String[] args) {
        Lamp first = new Lamp();
        Lamp second = new Lamp();
        Lamp alias = first;
        alias.turnOn();
        System.out.println(first.isOn());
        System.out.println(second.isOn());
    }
}
class Lamp {
    private boolean on = false;
    public Lamp() { on = false; }
    public void turnOn() { on = true; }
    public boolean isOn() { return on; }
}
