package StrategyPatternExample;

/**
 * PaymentContext - Context Class
 *
 * Holds a reference to the current PaymentStrategy and delegates
 * payment execution to it. The strategy can be swapped at runtime.
 */
public class PaymentContext {

    private PaymentStrategy paymentStrategy;

    public PaymentContext(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    /** Allows changing the payment strategy at runtime */
    public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
        System.out.println("[PaymentContext] Payment strategy switched to: "
                + paymentStrategy.getClass().getSimpleName());
    }

    /** Executes the payment using the currently set strategy */
    public void executePayment(double amount) {
        paymentStrategy.pay(amount);
    }
}
