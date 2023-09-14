package com.example.LaundrySystem.Controller.Sorting;

import com.example.LaundrySystem.Entities.Customer;
import com.example.LaundrySystem.Repositories.CustomerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class SortCustomer <T extends Comparable<T>> implements ISorterStrategy {
    @Autowired
    CustomerRepository customerRepo;
    @Override
    public List<Customer> sort(String sortBy, boolean order) {
        switch (sortBy){
            case "name":
                if(order) return customerRepo.findAllByOrderByNameAsc(); else return customerRepo.findAllByOrderByNameDesc();
            case "email":
                if(order) return customerRepo.findAllByOrderByEmailAsc(); else return customerRepo.findAllByOrderByEmailDesc();
            case "phoneNumber":
                if(order) return customerRepo.findAllByOrderByPhoneNumberAsc(); else return customerRepo.findAllByOrderByPhoneNumberDesc();
            case "address":
                if(order) return customerRepo.findAllByOrderByAddressAsc(); else return customerRepo.findAllByOrderByAddressDesc();
            case "totalPays":
                if(order) return customerRepo.findAllByOrderByTotalPaysAsc(); else return customerRepo.findAllByOrderByTotalPaysDesc();
            default:
                return null;
        }
    }
}
