package com.example.LaundrySystem.Controller.Filter;

import com.example.LaundrySystem.Controller.ServiceProvider.OrderAdapter;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class FilterService <T extends Comparable<T>> {
    @Autowired
    FilterEmployees filterEmployees;
    @Autowired
    FilterCustomers filterCustomers;
    @Autowired
    FilterOrders filterOrders;
    @Autowired
    OrderAdapter orderAdapter;

    public List<T> filter(String entityName, String criteria, String toMeet, String laundryName){
        try {
            return switch (entityName) {
                case "emp" -> filterEmployees.meetCriteria(criteria, toMeet, laundryName);
                case "cus" -> filterCustomers.meetCriteria(criteria, toMeet, laundryName);
                case "ord" -> orderAdapter.convert(filterOrders.meetCriteria(criteria, toMeet, laundryName));
                default -> null;
            };
        }catch (Exception e){
            return null;
        }
    }

}
