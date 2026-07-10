package StrategyPatternExample;

/**
 * PayPalPayment - Concrete Strategy
 *
 * Implements payment via PayPal.
 */
public class PayPalPayment implements PaymentStrategy {

    private final String email;

    public PayPalPayment(String email) {
        this.email = email;
    }

    @Override
    public void pay(double amount) {
        System.out.println("[PayPal] $" + String.format("%.2f", amount)
                + " paid via PayPal account: " + email);
    }
}
