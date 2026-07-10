package AdapterPatternExample;

/**
 * RazorpayAdapter - Adapter class
 *
 * Wraps RazorpayGateway and translates the PaymentProcessor interface calls
 * into Razorpay's proprietary initiateTransaction() method.
 */
public class RazorpayAdapter implements PaymentProcessor {
    private final RazorpayGateway razorpayGateway;
    private int orderCounter = 1000;

    public RazorpayAdapter(RazorpayGateway razorpayGateway) {
        this.razorpayGateway = razorpayGateway;
    }

    @Override
    public void processPayment(double amount) {
        // Generate a simple order ID and translate to Razorpay's API
        String orderId = "ORD-" + (orderCounter++);
        razorpayGateway.initiateTransaction(orderId, amount);
    }
}
