package shop;

import java.util.*;

public class CustomerRepository {
    private final Map<Integer, Customer> data = new HashMap<>();
    public void save(Customer c) { data.put(c.getId(), c); }
    public Customer find(int id) { return data.get(id); }
    public Collection<Customer> all() { return data.values(); }
}
