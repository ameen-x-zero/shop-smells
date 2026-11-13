package shop;

public class Invoice {
    private final int id; private final Order order; private final double total; private final String date;
    public Invoice(int id, Order order, double total, String date) {
        this.id = id; this.order = order; this.total = total; this.date = date;
    }
    public int getId() { return id; }
    public Order getOrder() { return order; }
    public double getTotal() { return total; }
    public String getDate() { return date; }
}
