package shop;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private final int id; private final Customer customer;
    private final List<OrderItem> items = new ArrayList<>();
    private OrderStatus status = OrderStatus.NEW;
    private double discount;
    private PaymentMethod paymentMethod;

    public Order(int id, Customer customer) { this.id = id; this.customer = customer; }
    public int getId() { return id; }
    public Customer getCustomer() { return customer; }
    public List<OrderItem> getItems() { return items; }
    public void addItem(OrderItem item) { items.add(item); }
    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }
    public double getDiscount() { return discount; }
    public void setDiscount(double discount) { this.discount = discount; }
    public PaymentMethod getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(PaymentMethod m) { this.paymentMethod = m; }
}
