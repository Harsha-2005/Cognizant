package AdapterPatternExample;

/**
 * StripeAdapter - Adapter class
 *
 * Wraps StripeGateway and translates the PaymentProcessor interface calls
 * into Stripe's proprietary charge() method.
 */
public class StripeAdapter implements PaymentProcessor {
    private final StripeGateway stripeGateway;

    public StripeAdapter(StripeGateway stripeGateway) {
        this.stripeGateway = stripeGateway;
    }

    @Override
    public void processPayment(double amount) {
        // Translate processPayment() → charge(currency, amount)
        stripeGateway.charge("USD", amount);
    }
}
