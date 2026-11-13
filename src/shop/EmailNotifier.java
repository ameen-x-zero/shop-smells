package shop;

/**
 * ⚠️ CODE SMELL: Code Clones (المجموعة 3 - الإشعارات)
 * EmailNotifier / SmsNotifier / WhatsAppNotifier
 * خطوات التحقق والتجهيز والإرسال نفسها مكررة، وما يتغير إلا اسم القناة.
 */
public class EmailNotifier {
    private int sentCount = 0;

    public boolean send(Customer customer, String message) {
        if (customer == null) {
            Logger.log("EMAIL failed: customer is null");
            return false;
        }
        if (message == null || message.trim().isEmpty()) {
            Logger.log("EMAIL failed: empty message");
            return false;
        }
        String target = customer.getEmail();
        if (target == null || target.trim().isEmpty()) {
            Logger.log("EMAIL failed: no contact info");
            return false;
        }
        String finalMessage = message.trim();
        if (finalMessage.length() > 160) {
            finalMessage = finalMessage.substring(0, 160);
        }
        System.out.println("[EMAIL] to " + target + ": " + finalMessage);
        sentCount++;
        return true;
    }

    public int getSentCount() { return sentCount; }
}
