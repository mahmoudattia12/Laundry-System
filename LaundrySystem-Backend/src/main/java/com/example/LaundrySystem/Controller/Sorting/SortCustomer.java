package com.example.LaundrySystem.Controller.Sorting;

import com.example.LaundrySystem.Entities.Customer;
import com.example.LaundrySystem.Repositories.CustomerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class SortCustomer <T extends Comparable<T>> implements ISorterStrategy {
    @Autowired
    CustomerRepository customerRepo;
    @Override
    public List<Customer> sort(String sortBy, boolean order, String laundryName) {
        try{
            switch (sortBy){
                case "name":
                    if(order) return customerRepo.findAllByLaundries_Name(laundryName, Sort.by(Sort.Direction.ASC, "name")); else return customerRepo.findAllByLaundries_Name(laundryName, Sort.by(Sort.Direction.DESC, "name"));
                case "email":
                    if(order) return customerRepo.findAllByLaundries_Name(laundryName, Sort.by(Sort.Direction.ASC, "email")); else return customerRepo.findAllByLaundries_Name(laundryName, Sort.by(Sort.Direction.DESC, "email"));
                case "phoneNumber":
                    if(order) return customerRepo.findAllByLaundries_Name(laundryName, Sort.by(Sort.Direction.ASC, "phoneNumber")); else return customerRepo.findAllByLaundries_Name(laundryName, Sort.by(Sort.Direction.DESC, "phoneNumber"));
                case "address":
                    if(order) return customerRepo.findAllByLaundries_Name(laundryName, Sort.by(Sort.Direction.ASC, "address")); else return customerRepo.findAllByLaundries_Name(laundryName, Sort.by(Sort.Direction.DESC, "address"));
//                case "totalPays":
//                    if(order) return customerRepo.findAllByOrderByTotalPaysAsc(); else return customerRepo.findAllByOrderByTotalPaysDesc();
                default:
                    return null;
            }
        }catch (Exception e){
            return null;
        }
    }
}
