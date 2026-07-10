package DecoratorPatternExample;

/**
 * SMSNotifierDecorator - Concrete Decorator
 *
 * Adds SMS notification on top of whatever Notifier it wraps.
 */
public class SMSNotifierDecorator extends NotifierDecorator {

    private final String phoneNumber;

    public SMSNotifierDecorator(Notifier notifier, String phoneNumber) {
        super(notifier);
        this.phoneNumber = phoneNumber;
    }

    @Override
    public void send(String message) {
        super.send(message);  // Send via the wrapped notifier first
        System.out.println("[SMS  → " + phoneNumber + "] " + message);
    }
}
