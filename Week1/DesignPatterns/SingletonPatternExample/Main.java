package SingletonPatternExample;

/**
 * Main - Tests the Singleton Pattern implementation.
 *
 * Verifies that only ONE instance of Logger is created and shared
 * across different parts of the application.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("=== Singleton Pattern - Logger Demo ===\n");

        // Retrieve the singleton instance from different "parts" of the app
        Logger logger1 = Logger.getInstance();
        Logger logger2 = Logger.getInstance();
        Logger logger3 = Logger.getInstance();

        // Verify all references point to the SAME object
        System.out.println("\n--- Instance Identity Check ---");
        System.out.println("logger1 == logger2 : " + (logger1 == logger2));
        System.out.println("logger2 == logger3 : " + (logger2 == logger3));
        System.out.println("Same instance?     : " + (logger1 == logger2 && logger2 == logger3));

        // Use the logger through different references
        System.out.println("\n--- Logging via logger1 ---");
        logger1.log("Application started successfully.");
        logger1.warn("Low memory warning detected.");

        System.out.println("\n--- Logging via logger2 ---");
        logger2.log("User 'admin' logged in.");
        logger2.error("Database connection failed!");

        System.out.println("\n--- Logging via logger3 ---");
        logger3.log("Request processed in 42ms.");
        logger3.warn("Deprecated API endpoint called.");

        System.out.println("\n--- Hash Codes (should be identical) ---");
        System.out.println("logger1 hashCode: " + System.identityHashCode(logger1));
        System.out.println("logger2 hashCode: " + System.identityHashCode(logger2));
        System.out.println("logger3 hashCode: " + System.identityHashCode(logger3));
    }
}
