package DecoratorPatternExample;

/**
 * SlackNotifierDecorator - Concrete Decorator
 *
 * Adds Slack notification on top of whatever Notifier it wraps.
 */
public class SlackNotifierDecorator extends NotifierDecorator {

    private final String slackChannel;

    public SlackNotifierDecorator(Notifier notifier, String slackChannel) {
        super(notifier);
        this.slackChannel = slackChannel;
    }

    @Override
    public void send(String message) {
        super.send(message);  // Send via the wrapped notifier first
        System.out.println("[Slack → #" + slackChannel + "] " + message);
    }
}
