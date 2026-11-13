package shop;

import java.util.*;

public class OrderRepository {
    private final Map<Integer, Order> data = new HashMap<>();
    public void save(Order o) { data.put(o.getId(), o); }
    public Order find(int id) { return data.get(id); }
    public List<Order> all() { return new ArrayList<>(data.values()); }
}
