package StrategyPatternExample;

/**
 * PaymentStrategy - Strategy Interface
 *
 * Defines the contract for all interchangeable payment algorithms.
 * The context (PaymentContext) depends only on this interface,
 * making it easy to swap strategies at runtime.
 */
public interface PaymentStrategy {
    /**
     * Executes the payment for the given amount.
     * @param amount The amount to be paid
     */
    void pay(double amount);
}
