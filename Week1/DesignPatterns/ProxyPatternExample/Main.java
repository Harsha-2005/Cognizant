package ProxyPatternExample;

/**
 * Main - Tests the Proxy Pattern for image loading with lazy init and caching.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("=== Proxy Pattern - Image Viewer Demo ===\n");

        // Create proxies — NO loading happens yet
        Image image1 = new ProxyImage("vacation_photo.jpg");
        Image image2 = new ProxyImage("profile_picture.png");

        System.out.println("\n--- Displaying image1 for the first time (triggers remote load) ---");
        image1.display();

        System.out.println("\n--- Displaying image1 again (served from cache) ---");
        image1.display();

        System.out.println("\n--- Displaying image1 a third time (still cached) ---");
        image1.display();

        System.out.println("\n--- Displaying image2 for the first time (triggers remote load) ---");
        image2.display();

        System.out.println("\n--- Displaying image2 again (served from cache) ---");
        image2.display();

        System.out.println("\n[Notice: Remote server was called only TWICE total — once per image]");
    }
}
