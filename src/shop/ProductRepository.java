package shop;

import java.util.*;

public class ProductRepository {
    private final Map<Integer, Product> data = new HashMap<>();
    public void save(Product p) { data.put(p.getId(), p); }
    public Product find(int id) { return data.get(id); }
    public Collection<Product> all() { return data.values(); }
}
