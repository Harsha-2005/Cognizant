package ProxyPatternExample;

/**
 * RealImage - Real Subject
 *
 * The actual image class that performs the heavy operation of loading
 * an image from a remote server. This is expensive and should only
 * happen once (cached by the proxy thereafter).
 */
public class RealImage implements Image {

    private final String filename;

    /**
     * The constructor simulates loading the image from a remote server.
     * This is the expensive operation that the proxy will defer/cache.
     */
    public RealImage(String filename) {
        this.filename = filename;
        loadFromRemoteServer();
    }

    private void loadFromRemoteServer() {
        System.out.println("[RealImage] Loading image from remote server: " + filename);
        // Simulating network latency
        try { Thread.sleep(500); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        System.out.println("[RealImage] Image loaded: " + filename);
    }

    @Override
    public void display() {
        System.out.println("[RealImage] Displaying: " + filename);
    }
}
