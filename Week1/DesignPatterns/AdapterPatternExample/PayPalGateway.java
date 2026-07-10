package AdapterPatternExample;

/**
 * PayPalGateway - Adaptee class (third-party)
 *
 * This class represents the PayPal payment gateway with its OWN proprietary API.
 * It cannot be used directly because its method signature differs from PaymentProcessor.
 */
public class PayPalGateway {
    public void makePayment(double amount) {
        System.out.println("[PayPal] Payment of $" + String.format("%.2f", amount) +
                           " sent via PayPal account.");
    }
}
