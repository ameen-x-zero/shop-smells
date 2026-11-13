package shop;

/**
 * ⚠️ CODE SMELL: Code Clones (المجموعة 1 - حاسبات الخصم)
 * هذا الكلاس نسخة شبه حرفية من StudentDiscountCalculator و EmployeeDiscountCalculator
 * و SeniorDiscountCalculator. الفرق الوحيد: نسبة الخصم (0.20) والمكافأة (100).
 */
public class EmployeeDiscountCalculator {

    public double calculate(Order order) {
        double subtotal = 0;
        for (OrderItem item : order.getItems()) {
            subtotal += item.getProduct().getPrice() * item.getQuantity();
        }
        double discount = subtotal * 0.20;
        if (subtotal > 1000) {
            discount += 100;
        }
        if (discount > subtotal) {
            discount = subtotal;
        }
        Logger.log("Employee discount for order " + order.getId() + " = " + discount);
        return discount;
    }
}
