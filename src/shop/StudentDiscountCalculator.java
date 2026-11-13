package shop;

/**
 * ⚠️ CODE SMELL: Code Clones (المجموعة 1 - حاسبات الخصم)
 * هذا الكلاس نسخة شبه حرفية من StudentDiscountCalculator و EmployeeDiscountCalculator
 * و SeniorDiscountCalculator. الفرق الوحيد: نسبة الخصم (0.10) والمكافأة (50).
 */
public class StudentDiscountCalculator {

    public double calculate(Order order) {
        double subtotal = 0;
        for (OrderItem item : order.getItems()) {
            subtotal += item.getProduct().getPrice() * item.getQuantity();
        }
        double discount = subtotal * 0.10;
        if (subtotal > 1000) {
            discount += 50;
        }
        if (discount > subtotal) {
            discount = subtotal;
        }
        Logger.log("Student discount for order " + order.getId() + " = " + discount);
        return discount;
    }
}
