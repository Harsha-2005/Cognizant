package AdapterPatternExample;

/**
 * PaymentProcessor - Target Interface
 *
 * This is the interface that the client code works with.
 * All payment gateways must be adapted to this standard interface.
 */
public interface PaymentProcessor {
    /**
     * Processes a payment for the specified amount.
     * @param amount The payment amount in USD
     */
    void processPayment(double amount);
}
