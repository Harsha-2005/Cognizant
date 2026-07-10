package ObserverPatternExample;

/**
 * Observer - Observer Interface
 *
 * All clients interested in stock price changes must implement this interface.
 */
public interface Observer {
    /**
     * Called by the subject whenever the stock price changes.
     * @param stockSymbol The ticker symbol (e.g., "AAPL")
     * @param newPrice    The updated stock price
     */
    void update(String stockSymbol, double newPrice);
}
