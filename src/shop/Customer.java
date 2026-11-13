package shop;

public class Customer {
    private final int id;
    private final String name, email, phone;
    private final String type;          // REGULAR / STUDENT / EMPLOYEE / SENIOR
    private final Address address;
    private int loyaltyPoints;

    public Customer(int id, String name, String email, String phone, String type, Address address) {
        this.id = id; this.name = name; this.email = email;
        this.phone = phone; this.type = type; this.address = address;
    }
    public int getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getType() { return type; }
    public Address getAddress() { return address; }
    public int getLoyaltyPoints() { return loyaltyPoints; }
    public void addLoyaltyPoints(int p) { loyaltyPoints += p; }
}
