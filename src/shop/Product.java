package shop;

public class Product {
    private final int id; private final String name; private final double price;
    private int stock; private final Category category;

    public Product(int id, String name, double price, int stock, Category category) {
        this.id = id; this.name = name; this.price = price; this.stock = stock; this.category = category;
    }
    public int getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
    public Category getCategory() { return category; }
}
