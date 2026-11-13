package shop;

import java.util.List;

/**
 * ⚠️ CODE SMELL: Code Clones (المجموعة 2 - مولدات التقارير)
 * PdfReportGenerator / ExcelReportGenerator / HtmlReportGenerator
 * منطق الحساب (الحلقة + الإجمالي + العدّاد) مكرر بالكامل، والمختلف فقط نص التنسيق.
 */
public class ExcelReportGenerator {

    public String generate(List<Order> orders) {
        StringBuilder sb = new StringBuilder();
        sb.append("[EXCEL] Sales Report\n");
        double total = 0;
        int count = 0;
        for (Order o : orders) {
            if (o.getStatus() == OrderStatus.CANCELLED) {
                continue;
            }
            double orderTotal = 0;
            for (OrderItem i : o.getItems()) {
                orderTotal += i.getProduct().getPrice() * i.getQuantity();
            }
            orderTotal -= o.getDiscount();
            sb.append("ROW;" + o.getId() + " | " + o.getCustomer().getName() + " | " + orderTotal + "\n");
            total += orderTotal;
            count++;
        }
        sb.append("[EXCEL] Orders: " + count + ", Total: " + total + "\n");
        Logger.log("EXCEL report generated");
        return sb.toString();
    }
}
