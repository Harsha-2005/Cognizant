package DecoratorPatternExample;

/**
 * Main - Tests the Decorator Pattern for the notification system.
 *
 * Decorators can be stacked in any order to combine multiple channels dynamically.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("=== Decorator Pattern - Notification System Demo ===\n");

        // Scenario 1: Email only
        Notifier emailOnly = new EmailNotifier("user@example.com");
        System.out.println("--- Email Only ---");
        emailOnly.send("Your order #1001 has been placed.");

        // Scenario 2: Email + SMS
        Notifier emailAndSms = new SMSNotifierDecorator(
                new EmailNotifier("user@example.com"),
                "+91-9876543210"
        );
        System.out.println("\n--- Email + SMS ---");
        emailAndSms.send("Your order #1002 has been shipped.");

        // Scenario 3: Email + SMS + Slack (all three channels)
        Notifier allChannels = new SlackNotifierDecorator(
                new SMSNotifierDecorator(
                        new EmailNotifier("admin@company.com"),
                        "+1-800-555-1234"
                ),
                "alerts"
        );
        System.out.println("\n--- Email + SMS + Slack ---");
        allChannels.send("CRITICAL: Production server is down!");

        // Scenario 4: Email + Slack (skipping SMS)
        Notifier emailAndSlack = new SlackNotifierDecorator(
                new EmailNotifier("dev@company.com"),
                "dev-notifications"
        );
        System.out.println("\n--- Email + Slack ---");
        emailAndSlack.send("New build deployed to staging environment.");
    }
}
