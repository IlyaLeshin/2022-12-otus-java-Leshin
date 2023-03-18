package ru.otus;

import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class CustomerService {

    private final NavigableMap<Customer, String> customerTreeMap = new TreeMap<>();

    public Map.Entry<Customer, String> getSmallest() {
        Customer smallestCustomer = customerTreeMap.firstEntry().getKey();
        String smallestCustomersData = customerTreeMap.get(smallestCustomer);
        return returnEntry(smallestCustomer, smallestCustomersData);
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        try {
            Customer nextCustomer = customerTreeMap.higherKey(customer);
            String nextCustomersData = customerTreeMap.get(nextCustomer);
            return returnEntry(nextCustomer, nextCustomersData);
        } catch (NullPointerException e) {
            return null;
        }
    }

    public void add(Customer customer, String data) {
        customerTreeMap.put(customer, data);
    }

    private Map.Entry<Customer, String> returnEntry(Customer customer, String data) {
        return Map.entry(new Customer(
                        customer.getId(),
                        customer.getName(),
                        customer.getScores()),
                data);
    }
}