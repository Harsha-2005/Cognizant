package ObserverPatternExample;

/**
 * MobileApp - Concrete Observer
 *
 * Represents a mobile application that receives stock price alerts.
 */
public class MobileApp implements Observer {

    private final String appName;

    public MobileApp(String appName) {
        this.appName = appName;
    }

    @Override
    public void update(String stockSymbol, double newPrice) {
        System.out.println("  [MobileApp: " + appName + "] 📱 Alert: "
                + stockSymbol + " is now $" + String.format("%.2f", newPrice));
    }
}
