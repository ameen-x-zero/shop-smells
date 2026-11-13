package shop;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * ⚠️⚠️ CODE SMELL: God Class ⚠️⚠️
 *
 * هذا الكلاس "يعرف كل شي ويسوي كل شي". مسؤولياته المختلطة:
 *   1) إدارة العملاء          2) إدارة المنتجات والمخزون
 *   3) إدارة الطلبات          4) حساب الخصومات
 *   5) حساب الضريبة والشحن    6) الدفع
 *   7) الفواتير               8) الإشعارات (إيميل / SMS)
 *   9) التقارير والإحصائيات  10) التحقق من البيانات (Validation)
 *
 * علامات الـ God Class الموجودة هنا:
 *   - كلاس ضخم (عدد الأسطر والدوال كبير)
 *   - حقول كثيرة (Low Cohesion)
 *   - يعتمد على أغلب كلاسات المشروع (High Coupling)
 *   - لو تغير أي شي (الضريبة، الإيميل، التقرير) لازم نعدّل هذا الكلاس
 *
 * الحل المقترح: Extract Class -> CustomerService, InventoryService, DiscountService,
 *               PaymentService, InvoiceService, NotificationService, ReportService.
 */
public class StoreManager {

    // ---------- حقول كثيرة ومتنوعة ----------
    private final CustomerRepository customerRepo = new CustomerRepository();
    private final ProductRepository productRepo = new ProductRepository();
    private final OrderRepository orderRepo = new OrderRepository();
    private final InvoiceRepository invoiceRepo = new InvoiceRepository();
    private final List<Payment> payments = new ArrayList<>();

    private static final double TAX_RATE = 0.15;
    private static final double FREE_SHIPPING_LIMIT = 500;
    private static final double SHIPPING_COST = 25;

    private double totalRevenue = 0;
    private int emailsSent = 0;
    private int smsSent = 0;
    private int ordersCreated = 0;
    private int ordersCancelled = 0;
    private int nextOrderId = 1;
    private int nextInvoiceId = 1;

    // ---------- 1) إدارة العملاء ----------
    public void addCustomer(Customer c) {
        if (!validateEmail(c.getEmail())) {
            Logger.log("Invalid email for customer " + c.getName());
            return;
        }
        if (!validatePhone(c.getPhone())) {
            Logger.log("Invalid phone for customer " + c.getName());
            return;
        }
        customerRepo.save(c);
    }

    public Customer findCustomer(int id) { return customerRepo.find(id); }

    public void addLoyaltyPoints(Customer c, double amount) {
        c.addLoyaltyPoints((int) (amount / 10));
    }

    // ---------- 2) المنتجات والمخزون ----------
    public void addProduct(Product p) { productRepo.save(p); }

    public boolean checkStock(Product p, int qty) { return p.getStock() >= qty; }

    public void reduceStock(Product p, int qty) { p.setStock(p.getStock() - qty); }

    public void restock(int productId, int qty) {
        Product p = productRepo.find(productId);
        if (p != null) {
            p.setStock(p.getStock() + qty);
            Logger.log("Restocked " + p.getName() + " +" + qty);
        }
    }

    public List<Product> lowStockProducts(int threshold) {
        List<Product> result = new ArrayList<>();
        for (Product p : productRepo.all()) {
            if (p.getStock() < threshold) result.add(p);
        }
        return result;
    }

    // ---------- 3) إدارة الطلبات ----------
    public Order createOrder(int customerId) {
        Customer c = customerRepo.find(customerId);
        if (c == null) return null;
        Order o = new Order(nextOrderId++, c);
        orderRepo.save(o);
        ordersCreated++;
        return o;
    }

    public boolean addItemToOrder(Order o, int productId, int qty) {
        Product p = productRepo.find(productId);
        if (p == null || !checkStock(p, qty)) {
            Logger.log("Cannot add product " + productId);
            return false;
        }
        o.addItem(new OrderItem(p, qty));
        reduceStock(p, qty);
        return true;
    }

    public void cancelOrder(Order o) {
        if (o.getStatus() == OrderStatus.SHIPPED) {
            Logger.log("Cannot cancel shipped order");
            return;
        }
        for (OrderItem i : o.getItems()) {
            i.getProduct().setStock(i.getProduct().getStock() + i.getQuantity());
        }
        o.setStatus(OrderStatus.CANCELLED);
        ordersCancelled++;
        sendEmail(o.getCustomer(), "Your order " + o.getId() + " was cancelled");
    }

    public void shipOrder(Order o) {
        if (o.getStatus() != OrderStatus.PAID) {
            Logger.log("Order must be paid first");
            return;
        }
        o.setStatus(OrderStatus.SHIPPED);
        sendSms(o.getCustomer(), "Order " + o.getId() + " is on its way!");
    }

    // ---------- 4) الخصومات (منطق مكرر أيضاً داخل الـ God Class) ----------
    public double calculateDiscount(Order o) {
        double subtotal = calculateSubtotal(o);
        String type = o.getCustomer().getType();
        double discount = 0;
        if (type.equals("STUDENT")) {
            discount = subtotal * 0.10;
            if (subtotal > 1000) discount += 50;
        } else if (type.equals("EMPLOYEE")) {
            discount = subtotal * 0.20;
            if (subtotal > 1000) discount += 100;
        } else if (type.equals("SENIOR")) {
            discount = subtotal * 0.15;
            if (subtotal > 1000) discount += 75;
        }
        if (discount > subtotal) discount = subtotal;
        o.setDiscount(discount);
        return discount;
    }

    // ---------- 5) الضريبة والشحن ----------
    public double calculateSubtotal(Order o) {
        double s = 0;
        for (OrderItem i : o.getItems()) s += i.getTotal();
        return s;
    }

    public double calculateTax(Order o) {
        return (calculateSubtotal(o) - o.getDiscount()) * TAX_RATE;
    }

    public double calculateShipping(Order o) {
        return calculateSubtotal(o) >= FREE_SHIPPING_LIMIT ? 0 : SHIPPING_COST;
    }

    public double calculateTotal(Order o) {
        return calculateSubtotal(o) - o.getDiscount() + calculateTax(o) + calculateShipping(o);
    }

    // ---------- 6) الدفع ----------
    public boolean processPayment(Order o, PaymentMethod method) {
        calculateDiscount(o);
        double total = calculateTotal(o);
        boolean ok;
        if (method == PaymentMethod.CASH) {
            ok = true;
        } else if (method == PaymentMethod.CARD) {
            ok = total < 10000;
        } else {
            ok = total < 3000;
        }
        payments.add(new Payment(o.getId(), total, method, ok));
        if (ok) {
            o.setPaymentMethod(method);
            o.setStatus(OrderStatus.PAID);
            totalRevenue += total;
            addLoyaltyPoints(o.getCustomer(), total);
            generateInvoice(o);
            sendEmail(o.getCustomer(), "Payment received for order " + o.getId() + ": " + total);
        }
        return ok;
    }

    // ---------- 7) الفواتير ----------
    public Invoice generateInvoice(Order o) {
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        Invoice inv = new Invoice(nextInvoiceId++, o, calculateTotal(o), date);
        invoiceRepo.save(inv);
        return inv;
    }

    public void printInvoice(Invoice inv) {
        System.out.println("=========== INVOICE #" + inv.getId() + " ===========");
        System.out.println("Date: " + inv.getDate());
        System.out.println("Customer: " + inv.getOrder().getCustomer().getName());
        for (OrderItem i : inv.getOrder().getItems()) {
            System.out.println(" - " + i.getProduct().getName() + " x" + i.getQuantity() + " = " + i.getTotal());
        }
        System.out.println("Discount: " + inv.getOrder().getDiscount());
        System.out.println("TOTAL: " + inv.getTotal());
    }

    // ---------- 8) الإشعارات ----------
    public void sendEmail(Customer c, String msg) {
        if (c == null || msg == null || msg.trim().isEmpty()) return;
        System.out.println("[EMAIL] to " + c.getEmail() + ": " + msg);
        emailsSent++;
    }

    public void sendSms(Customer c, String msg) {
        if (c == null || msg == null || msg.trim().isEmpty()) return;
        System.out.println("[SMS] to " + c.getPhone() + ": " + msg);
        smsSent++;
    }

    // ---------- 9) التقارير والإحصائيات ----------
    public String generateSalesReport() {
        StringBuilder sb = new StringBuilder("=== SALES REPORT ===\n");
        for (Order o : orderRepo.all()) {
            if (o.getStatus() == OrderStatus.CANCELLED) continue;
            sb.append("Order ").append(o.getId()).append(" | ")
              .append(o.getCustomer().getName()).append(" | ").append(calculateTotal(o)).append("\n");
        }
        sb.append("Revenue: ").append(totalRevenue);
        return sb.toString();
    }

    public String generateStatistics() {
        return "Orders created: " + ordersCreated + ", cancelled: " + ordersCancelled
             + ", emails: " + emailsSent + ", sms: " + smsSent + ", revenue: " + totalRevenue;
    }

    public List<Order> allOrders() { return orderRepo.all(); }

    // ---------- 10) التحقق من البيانات ----------
    public boolean validateEmail(String email) {
        return email != null && email.contains("@") && email.contains(".");
    }

    public boolean validatePhone(String phone) {
        return phone != null && phone.length() >= 9 && phone.matches("[0-9+]+");
    }
}
