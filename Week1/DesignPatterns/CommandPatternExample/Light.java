package CommandPatternExample;

/**
 * Light - Receiver Class
 *
 * The actual device that knows how to perform the real work.
 * Commands delegate their execution to the Receiver.
 */
public class Light {

    private final String location;

    public Light(String location) {
        this.location = location;
    }

    public void turnOn() {
        System.out.println("[Light] " + location + " light turned ON 💡");
    }

    public void turnOff() {
        System.out.println("[Light] " + location + " light turned OFF 🌑");
    }
}
