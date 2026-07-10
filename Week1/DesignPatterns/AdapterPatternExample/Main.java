package AdapterPatternExample;

/**
 * Main - Tests the Adapter Pattern with multiple payment gateways.
 *
 * The client code only uses PaymentProcessor interface — it never knows
 * about the internal gateway implementations. This is the power of the Adapter Pattern.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("=== Adapter Pattern - Payment Gateway Demo ===\n");

        // Client works exclusively through the PaymentProcessor interface
        PaymentProcessor paypal  = new PayPalAdapter(new PayPalGateway());
        PaymentProcessor stripe  = new StripeAdapter(new StripeGateway());
        PaymentProcessor razorpay = new RazorpayAdapter(new RazorpayGateway());

        System.out.println("--- Processing payment via PayPal ---");
        paypal.processPayment(150.00);

        System.out.println("\n--- Processing payment via Stripe ---");
        stripe.processPayment(299.99);

        System.out.println("\n--- Processing payment via Razorpay ---");
        razorpay.processPayment(4999.00);

        System.out.println("\n--- Second Razorpay payment (new order ID) ---");
        razorpay.processPayment(1200.00);

        System.out.println("\n[All payments processed using unified PaymentProcessor interface]");
    }
}
