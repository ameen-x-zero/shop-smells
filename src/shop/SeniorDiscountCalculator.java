package shop;

/**
 * ⚠️ CODE SMELL: Code Clones (المجموعة 1 - حاسبات الخصم)
 * هذا الكلاس نسخة شبه حرفية من StudentDiscountCalculator و EmployeeDiscountCalculator
 * و SeniorDiscountCalculator. الفرق الوحيد: نسبة الخصم (0.15) والمكافأة (75).
 */
public class SeniorDiscountCalculator {

    public double calculate(Order order) {
        double subtotal = 0;
        for (OrderItem item : order.getItems()) {
            subtotal += item.getProduct().getPrice() * item.getQuantity();
        }
        double discount = subtotal * 0.15;
        if (subtotal > 1000) {
            discount += 75;
        }
        if (discount > subtotal) {
            discount = subtotal;
        }
        Logger.log("Senior discount for order " + order.getId() + " = " + discount);
        return discount;
    }
}
