package com.example.LaundrySystem.Controller.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilterService <T extends Comparable<T>> {
    @Autowired
    FilterEmployees filterEmployees;
    @Autowired
    FilterCustomers filterCustomers;
    @Autowired
    FilterOrders filterOrders;

    public List<T> filter(String entityName, String criteria, String toMeet){
        try {
            return switch (entityName) {
                case "emp" -> filterEmployees.meetCriteria(criteria, toMeet);
                case "cus" -> filterCustomers.meetCriteria(criteria, toMeet);
                case "ord" -> filterOrders.meetCriteria(criteria, toMeet);
                default -> null;
            };
        }catch (Exception e){
            return null;
        }
    }

}
