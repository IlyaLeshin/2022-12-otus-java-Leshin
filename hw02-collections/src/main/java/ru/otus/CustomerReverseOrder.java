package ru.otus;

import java.util.Deque;
import java.util.LinkedList;

public class CustomerReverseOrder {

    Deque<Customer> customersDeque = new LinkedList<>();

    public void add(Customer customer) {
        customersDeque.addFirst(customer);
    }

    public Customer take() {
        return customersDeque.removeFirst();
    }
}