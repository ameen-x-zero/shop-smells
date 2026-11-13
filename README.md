# Online Shop – Code Smells Demo (Java)

مشروع جافا فيه **26 كلاس** مصمم لعرض اثنين من الـ Code Smells بشكل واضح.

## 1) God Class
**الملف:** `src/shop/StoreManager.java` (~250 سطر)
يجمع 10 مسؤوليات: العملاء، المخزون، الطلبات، الخصومات، الضريبة والشحن، الدفع، الفواتير، الإشعارات، التقارير، التحقق.
**الحل:** Extract Class إلى CustomerService / InventoryService / DiscountService / PaymentService / InvoiceService / NotificationService / ReportService.

## 2) Code Clones
| المجموعة | الكلاسات | المكرر |
|---|---|---|
| حاسبات الخصم | Student/Employee/SeniorDiscountCalculator | كامل منطق `calculate()` والفرق في رقمين فقط |
| مولدات التقارير | Pdf/Excel/HtmlReportGenerator | الحلقة وحساب الإجمالي والعدّاد |
| الإشعارات | Email/Sms/WhatsAppNotifier | خطوات التحقق والإرسال |
| (إضافي) | `StoreManager.calculateDiscount` | يكرر منطق حاسبات الخصم الثلاثة |

**الحل:** Extract Superclass / Template Method / Strategy مع باراميتر للنسبة.

## بقية الكلاسات (نظيفة)
Address, Category, Customer, Product, Order, OrderItem, Payment, Invoice, OrderStatus, PaymentMethod,
CustomerRepository, ProductRepository, OrderRepository, InvoiceRepository, Logger, Main.

## التشغيل
```
javac -d out src/shop/*.java
java -cp out shop.Main
```
