package StrategyPatternExample;

/**
 * Main - Tests the Strategy Pattern with switchable payment methods.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("=== Strategy Pattern - Payment System Demo ===\n");

        // Start with Credit Card strategy
        PaymentContext context = new PaymentContext(
                new CreditCardPayment("4111111111111234", "Alice Johnson")
        );

        System.out.println("--- Paying for Order #1001 ---");
        context.executePayment(250.00);

        // Switch to PayPal at runtime
        System.out.println("\n--- Switching payment method ---");
        context.setPaymentStrategy(new PayPalPayment("alice@example.com"));

        System.out.println("\n--- Paying for Order #1002 ---");
        context.executePayment(89.99);

        // Switch back to a different credit card
        System.out.println("\n--- Switching back to Credit Card ---");
        context.setPaymentStrategy(new CreditCardPayment("5500000000005678", "Alice Johnson"));

        System.out.println("\n--- Paying for Order #1003 ---");
        context.executePayment(499.00);
    }
}
