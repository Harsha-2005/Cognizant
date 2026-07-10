package SingletonPatternExample;

/**
 * Logger - Singleton Pattern Implementation
 *
 * The Singleton Pattern ensures that a class has only ONE instance throughout
 * the application lifecycle, and provides a global access point to that instance.
 *
 * This is thread-safe using double-checked locking.
 */
public class Logger {

    // The single static instance - volatile ensures visibility across threads
    private static volatile Logger instance;

    // Private constructor prevents external instantiation
    private Logger() {
        System.out.println("Logger instance created.");
    }

    /**
     * Returns the single instance of Logger.
     * Uses double-checked locking for thread safety without performance overhead.
     */
    public static Logger getInstance() {
        if (instance == null) {                    // First check (no lock)
            synchronized (Logger.class) {
                if (instance == null) {            // Second check (with lock)
                    instance = new Logger();
                }
            }
        }
        return instance;
    }

    /** Logs an INFO level message */
    public void log(String message) {
        System.out.println("[INFO]  " + message);
    }

    /** Logs a WARNING level message */
    public void warn(String message) {
        System.out.println("[WARN]  " + message);
    }

    /** Logs an ERROR level message */
    public void error(String message) {
        System.out.println("[ERROR] " + message);
    }
}
