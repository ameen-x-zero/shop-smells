# Online Shop – Code Smells Demo (Java)

A Java project containing **26 classes**, designed to demonstrate two Code Smells clearly.

## 1. God Class

**File:** `src/shop/StoreManager.java` (~250 lines)

Combines 10 different responsibilities:
- Customer management
- Inventory management
- Order processing
- Discount calculations
- Tax and shipping calculations
- Payment processing
- Invoice generation
- Notifications
- Report generation
- Validation

**Proposed Solution:** Apply **Extract Class** to separate responsibilities into dedicated classes:

`CustomerService`, `InventoryService`, `DiscountService`, `PaymentService`, `InvoiceService`, `NotificationService`, and `ReportService`.

## 2. Code Clones

| Group | Classes | Duplicated Code |
|---|---|---|
| Discount Calculators | Student/Employee/SeniorDiscountCalculator | Entire `calculate()` logic, differing only in two numeric values |
| Report Generators | Pdf/Excel/HtmlReportGenerator | Report loop, total calculation, and counter |
| Notification Services | Email/Sms/WhatsAppNotifier | Validation and sending steps |
| Additional | `StoreManager.calculateDiscount` | Duplicates the discount logic found in the three discount calculators |

**Proposed Solution:** Apply **Extract Superclass**, **Template Method**, or **Strategy Pattern** with a configurable discount rate.

## 3. Remaining Classes (Clean)

Address, Category, Customer, Product, Order, OrderItem, Payment, Invoice, OrderStatus, PaymentMethod, CustomerRepository, ProductRepository, OrderRepository, InvoiceRepository, Logger, Main.

## 4. How to Run

```bash
javac -d out src/shop/*.java
java -cp out shop.Main
```

## 5. How to View Analysis Results

The analysis results have already been generated and saved in text files. To display them in PowerShell, run the following commands from the project root directory:

| Analysis | Command |
|---|---|
| Code Clones (CPD) | `Get-Content .\cpd-results.txt` |
| God Class (PMD) | `Get-Content .\pmd-godclass-results.txt` |
| LOC Results | `Get-Content .\loc-results.txt` |
| Coupling and NCSS Metrics | `Get-Content .\metrics-results.txt` |

### Display Specific Metrics

To display only Coupling results:

```powershell
Get-Content .\metrics-results.txt | Select-String 'CouplingBetweenObjects'
```

To display only NCSS results:

```powershell
Get-Content .\metrics-results.txt | Select-String 'NCSS'
```

To display the saved Coupling CSV table:

```powershell
Import-Csv .\coupling-results.csv | Format-Table -AutoSize
```

**Note:** These commands only display previously generated results. They do not rerun the analysis.