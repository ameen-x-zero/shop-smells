package shop;

/**
 * ⚠️ CODE SMELL: Code Clones (المجموعة 3 - الإشعارات)
 * EmailNotifier / SmsNotifier / WhatsAppNotifier
 * خطوات التحقق والتجهيز والإرسال نفسها مكررة، وما يتغير إلا اسم القناة.
 */
public class SmsNotifier {
    private int sentCount = 0;

    public boolean send(Customer customer, String message) {
        if (customer == null) {
            Logger.log("SMS failed: customer is null");
            return false;
        }
        if (message == null || message.trim().isEmpty()) {
            Logger.log("SMS failed: empty message");
            return false;
        }
        String target = customer.getPhone();
        if (target == null || target.trim().isEmpty()) {
            Logger.log("SMS failed: no contact info");
            return false;
        }
        String finalMessage = message.trim();
        if (finalMessage.length() > 160) {
            finalMessage = finalMessage.substring(0, 160);
        }
        System.out.println("[SMS] to " + target + ": " + finalMessage);
        sentCount++;
        return true;
    }

    public int getSentCount() { return sentCount; }
}
