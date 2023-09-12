package com.example.LaundrySystem.Controller.Sorting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SortServices<T extends Comparable<T>> {
    @Autowired
    SortEmployee sortEmployee;
    @Autowired
    SortOrder sortOrder;
    @Autowired
    SortCustomer sortCustomer;

    public List<T> sort(String entity, String sortBy, boolean order){
        try {
            return switch (entity) {
                case "emp" -> sortEmployee.sort(sortBy, order);
                case "cus" -> sortCustomer.sort(sortBy, order);
                case "ord" -> sortOrder.sort(sortBy, order);
                default -> null;
            };
        }catch (Exception e){
            return null;
        }
    }
}
