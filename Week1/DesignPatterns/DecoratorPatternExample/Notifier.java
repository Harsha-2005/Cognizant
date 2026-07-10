package DecoratorPatternExample;

/**
 * Notifier - Component Interface
 *
 * Defines the core contract for all notification channels.
 * Both concrete notifiers and decorators implement this interface.
 */
public interface Notifier {
    void send(String message);
}
