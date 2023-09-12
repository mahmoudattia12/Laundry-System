package com.example.LaundrySystem.Controller.Filter;

import com.example.LaundrySystem.Entities.Customer;
import com.example.LaundrySystem.Repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilterCustomers <T extends Comparable<T>> implements IFilter {
    @Autowired
    CustomerRepository customerRepo;
    @Override
    public List<Customer> meetCriteria(String criteria, String toMeet) {
        return switch (criteria) {
            case "name" -> customerRepo.findByName(toMeet);
            case "email" -> customerRepo.findByEmail(toMeet);
            case "phoneNumber" -> customerRepo.findByPhoneNumber(toMeet);
            case "address" -> customerRepo.findByAddress(toMeet);
            case "totalPays" -> customerRepo.findByTotalPays(toMeet);
            case "isGold" -> customerRepo.findByIsGoldCustomer(toMeet);
            case "search" -> customerRepo.findByAttributesContaining(toMeet);
            default -> null;
        };
    }
}
