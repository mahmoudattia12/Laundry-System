package com.example.LaundrySystem.Controller.Filter;

import com.example.LaundrySystem.Entities.Customer;
import com.example.LaundrySystem.Repositories.CustomerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class FilterCustomers <T extends Comparable<T>> implements IFilter {
    @Autowired
    CustomerRepository customerRepo;
    @Override
    public List<Customer> meetCriteria(String criteria, String toMeet, String laundryName) {
        try{
            return switch (criteria) {
                case "name" -> customerRepo.findByLaundries_NameAndName(laundryName, toMeet);
                case "email" -> customerRepo.findByLaundries_NameAndEmail(laundryName, toMeet);
                case "phoneNumber" -> customerRepo.findByLaundries_NameAndPhoneNumber(laundryName, toMeet);
                case "address" -> customerRepo.findByLaundries_NameAndAddress(laundryName, toMeet);
                case "search" -> customerRepo.findByAttributesContainingAndLaundryName(laundryName, toMeet);
                default -> null;
            };
        }catch (Exception e){
            return null;
        }
    }
}
