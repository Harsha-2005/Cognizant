package ProxyPatternExample;

/**
 * ProxyImage - Proxy Class
 *
 * Implements lazy initialization and caching:
 * -> Lazy Initialization : The RealImage is NOT created until display() is called for the first time.
 * -> Caching             : After loading, the RealImage reference is kept and reused on subsequent calls,
 *                          avoiding the expensive remote server load.
 */
public class ProxyImage implements Image {

    private final String filename;
    private RealImage realImage; // null until first display() call

    public ProxyImage(String filename) {
        this.filename = filename;
        // NOTE: We do NOT load the image here (lazy initialization)
        System.out.println("[ProxyImage] Proxy created for: " + filename + " (not loaded yet)");
    }

    @Override
    public void display() {
        if (realImage == null) {
            // Lazy initialization: load only on first display request
            System.out.println("[ProxyImage] First request — loading from remote server...");
            realImage = new RealImage(filename);
        } else {
            // Cache hit: reuse the already-loaded image
            System.out.println("[ProxyImage] Cache hit — using cached image.");
        }
        realImage.display();
    }
}
