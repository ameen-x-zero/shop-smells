package shop;

import java.util.*;

public class InvoiceRepository {
    private final Map<Integer, Invoice> data = new HashMap<>();
    public void save(Invoice i) { data.put(i.getId(), i); }
    public Invoice find(int id) { return data.get(id); }
    public Collection<Invoice> all() { return data.values(); }
}
