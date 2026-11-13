package shop;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        StoreManager store = new StoreManager();
        Category electronics = new Category(1, "Electronics");
        Category books = new Category(2, "Books");

        store.addProduct(new Product(1, "Laptop", 3500, 10, electronics));
        store.addProduct(new Product(2, "Phone", 2000, 20, electronics));
        store.addProduct(new Product(3, "Java Book", 120, 50, books));

        Address addr = new Address("King Rd", "Jeddah", "KSA");
        store.addCustomer(new Customer(1, "Ahmed", "ahmed@mail.com", "0501234567", "STUDENT", addr));
        store.addCustomer(new Customer(2, "Sara", "sara@mail.com", "0507654321", "EMPLOYEE", addr));

        Order o1 = store.createOrder(1);
        store.addItemToOrder(o1, 1, 1);
        store.addItemToOrder(o1, 3, 2);
        store.processPayment(o1, PaymentMethod.CARD);

        Order o2 = store.createOrder(2);
        store.addItemToOrder(o2, 2, 2);
        store.processPayment(o2, PaymentMethod.CASH);
        store.shipOrder(o2);

        store.printInvoice(store.generateInvoice(o1));
        System.out.println(store.generateSalesReport());
        System.out.println(store.generateStatistics());

     
        System.out.println("\n--- Discount clones ---");
        System.out.println(new StudentDiscountCalculator().calculate(o1));
        System.out.println(new EmployeeDiscountCalculator().calculate(o2));
        System.out.println(new SeniorDiscountCalculator().calculate(o2));

        System.out.println("\n--- Report clones ---");
        List<Order> orders = store.allOrders();
        System.out.println(new PdfReportGenerator().generate(orders));
        System.out.println(new ExcelReportGenerator().generate(orders));
        System.out.println(new HtmlReportGenerator().generate(orders));

        System.out.println("--- Notifier clones ---");
        Customer c = o1.getCustomer();
        new EmailNotifier().send(c, "Hello from the shop");
        new SmsNotifier().send(c, "Hello from the shop");
        new WhatsAppNotifier().send(c, "Hello from the shop");
    }
}
