package StrategyPatternExample;

/**
 * CreditCardPayment - Concrete Strategy
 *
 * Implements payment via credit card.
 */
public class CreditCardPayment implements PaymentStrategy {

    private final String cardNumber;
    private final String cardHolder;

    public CreditCardPayment(String cardNumber, String cardHolder) {
        this.cardNumber = cardNumber;
        this.cardHolder = cardHolder;
    }

    @Override
    public void pay(double amount) {
        System.out.println("[Credit Card] Charged $" + String.format("%.2f", amount)
                + " to card ending in " + cardNumber.substring(cardNumber.length() - 4)
                + " | Holder: " + cardHolder);
    }
}
