package ObserverPatternExample;

/**
 * WebApp - Concrete Observer
 *
 * Represents a web application dashboard that updates stock price displays.
 */
public class WebApp implements Observer {

    private final String dashboardName;

    public WebApp(String dashboardName) {
        this.dashboardName = dashboardName;
    }

    @Override
    public void update(String stockSymbol, double newPrice) {
        System.out.println("  [WebApp: " + dashboardName + "] 🌐 Dashboard updated: "
                + stockSymbol + " = $" + String.format("%.2f", newPrice));
    }
}
