package AdapterPatternExample;

/**
 * PayPalAdapter - Adapter class
 *
 * Wraps PayPalGateway and translates the PaymentProcessor interface calls
 * into PayPal's proprietary makePayment() method.
 */
public class PayPalAdapter implements PaymentProcessor {
    private final PayPalGateway payPalGateway;

    public PayPalAdapter(PayPalGateway payPalGateway) {
        this.payPalGateway = payPalGateway;
    }

    @Override
    public void processPayment(double amount) {
        // Translate processPayment() → makePayment()
        payPalGateway.makePayment(amount);
    }
}
