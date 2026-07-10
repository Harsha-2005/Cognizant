package ObserverPatternExample;

/**
 * Main - Tests the Observer Pattern for stock market price monitoring.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("=== Observer Pattern - Stock Market Demo ===\n");

        // Create the subject (observable)
        StockMarket appleStock = new StockMarket("AAPL", 185.00);

        // Create observers
        Observer mobileApp1 = new MobileApp("StockTracker Pro");
        Observer mobileApp2 = new MobileApp("WealthWatch");
        Observer webDashboard = new WebApp("Trading Dashboard");

        // Register observers
        System.out.println("--- Registering Observers ---");
        appleStock.registerObserver(mobileApp1);
        appleStock.registerObserver(mobileApp2);
        appleStock.registerObserver(webDashboard);

        // Trigger price changes — all observers get notified
        System.out.println("\n--- Price Change Events ---");
        appleStock.setStockPrice(192.50);
        appleStock.setStockPrice(188.75);

        // Deregister one observer
        System.out.println("\n--- Deregistering WealthWatch ---");
        appleStock.deregisterObserver(mobileApp2);

        // Only remaining observers get notified
        appleStock.setStockPrice(200.00);

        System.out.println("\n[WealthWatch did NOT receive the $200.00 update]");
    }
}
