package shop;

public class Payment {
    private final int orderId; private final double amount;
    private final PaymentMethod method; private final boolean paid;
    public Payment(int orderId, double amount, PaymentMethod method, boolean paid) {
        this.orderId = orderId; this.amount = amount; this.method = method; this.paid = paid;
    }
    public int getOrderId() { return orderId; }
    public double getAmount() { return amount; }
    public PaymentMethod getMethod() { return method; }
    public boolean isPaid() { return paid; }
}
