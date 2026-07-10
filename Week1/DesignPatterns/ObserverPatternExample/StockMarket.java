package ObserverPatternExample;

import java.util.ArrayList;
import java.util.List;

/**
 * StockMarket - Concrete Subject
 *
 * Maintains the stock price and notifies all registered observers
 * whenever the price changes.
 */
public class StockMarket implements Stock {

    private final String stockSymbol;
    private double stockPrice;
    private final List<Observer> observers = new ArrayList<>();

    public StockMarket(String stockSymbol, double initialPrice) {
        this.stockSymbol = stockSymbol;
        this.stockPrice  = initialPrice;
    }

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
        System.out.println("[StockMarket] Observer registered for " + stockSymbol);
    }

    @Override
    public void deregisterObserver(Observer observer) {
        observers.remove(observer);
        System.out.println("[StockMarket] Observer deregistered from " + stockSymbol);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(stockSymbol, stockPrice);
        }
    }

    /** Updates the stock price and automatically notifies all observers */
    public void setStockPrice(double newPrice) {
        System.out.println("\n[StockMarket] " + stockSymbol + " price changed: $"
                + String.format("%.2f", stockPrice) + " → $" + String.format("%.2f", newPrice));
        this.stockPrice = newPrice;
        notifyObservers();
    }

    public double getStockPrice() { return stockPrice; }
    public String getStockSymbol() { return stockSymbol; }
}
