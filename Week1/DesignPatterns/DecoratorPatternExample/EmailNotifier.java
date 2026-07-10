package DecoratorPatternExample;

/**
 * EmailNotifier - Concrete Component
 *
 * The base notification channel. Sends notifications via Email.
 * This is the core component that decorators will wrap around.
 */
public class EmailNotifier implements Notifier {

    private final String emailAddress;

    public EmailNotifier(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @Override
    public void send(String message) {
        System.out.println("[Email → " + emailAddress + "] " + message);
    }
}
