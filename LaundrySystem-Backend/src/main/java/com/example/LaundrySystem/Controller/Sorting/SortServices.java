package com.example.LaundrySystem.Controller.Sorting;

import com.example.LaundrySystem.Controller.ServiceProvider.OrderAdapter;
import com.example.LaundrySystem.Entities.Order;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class SortServices<T extends Comparable<T>> {
    @Autowired
    SortEmployee sortEmployee;
    @Autowired
    SortOrder sortOrder;
    @Autowired
    SortCustomer sortCustomer;
    @Autowired
    OrderAdapter orderAdapter;

    public List<T> sort(String entity, String sortBy, boolean order, String laundryName){
        try {
            return switch (entity) {
                case "emp" -> sortEmployee.sort(sortBy, order, laundryName);
                case "cus" -> sortCustomer.sort(sortBy, order, laundryName);
                case "ord" -> orderAdapter.convert(sortOrder.sort(sortBy, order, laundryName));
                default -> null;
            };
        }catch (Exception e){
            return null;
        }
    }
}
