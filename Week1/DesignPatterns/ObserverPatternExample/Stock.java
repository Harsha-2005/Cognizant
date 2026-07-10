package ObserverPatternExample;

/**
 * Stock - Subject Interface
 *
 * Defines the contract for the observable (subject) in the Observer Pattern.
 * Any class implementing this can be observed by multiple Observer objects.
 */
public interface Stock {
    void registerObserver(Observer observer);
    void deregisterObserver(Observer observer);
    void notifyObservers();
}
