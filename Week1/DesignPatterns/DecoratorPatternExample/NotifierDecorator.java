package DecoratorPatternExample;

/**
 * NotifierDecorator - Abstract Decorator
 *
 * Holds a reference to a wrapped Notifier object and delegates the send()
 * call to it. Concrete decorators extend this class to add extra behaviour.
 */
public abstract class NotifierDecorator implements Notifier {

    // The wrapped notifier (could be a base notifier or another decorator)
    protected final Notifier wrappedNotifier;

    public NotifierDecorator(Notifier notifier) {
        this.wrappedNotifier = notifier;
    }

    @Override
    public void send(String message) {
        // Delegate to the wrapped notifier by default
        wrappedNotifier.send(message);
    }
}
