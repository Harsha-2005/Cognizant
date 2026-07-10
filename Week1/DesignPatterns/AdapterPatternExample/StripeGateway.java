package AdapterPatternExample;

/**
 * StripeGateway - Adaptee class (third-party)
 *
 * Stripe's proprietary API uses 'charge()' instead of 'processPayment()'.
 */
public class StripeGateway {
    public void charge(String currency, double amount) {
        System.out.println("[Stripe] Charged " + String.format("%.2f", amount) +
                           " " + currency + " via Stripe.");
    }
}
