package AdapterPatternExample;

/**
 * RazorpayGateway - Adaptee class (third-party)
 *
 * Razorpay uses 'initiateTransaction()' with order ID and amount.
 */
public class RazorpayGateway {
    public void initiateTransaction(String orderId, double amount) {
        System.out.println("[Razorpay] Transaction initiated | Order: " + orderId +
                           " | Amount: ₹" + String.format("%.2f", amount));
    }
}
