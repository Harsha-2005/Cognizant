package ProxyPatternExample;

/**
 * Image - Subject Interface
 *
 * Defines the contract for both RealImage and ProxyImage.
 * The client works through this interface without knowing which implementation it uses.
 */
public interface Image {
    void display();
}
