package com.example.LaundrySystem.Controller.ServiceProvider;

import com.example.LaundrySystem.Entities.Customer;
import com.example.LaundrySystem.Entities.Order;
import com.example.LaundrySystem.Repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServices {
    @Autowired
    CustomerRepository customerRepo;
    public String add(Customer customer){
        try {
            Optional<Customer> cus = customerRepo.findById(customer.getPhoneNumber());
            if(cus.isPresent()){
                return "Already Exists";
            }else{
                customerRepo.save(customer);
                return "SUCCESS";
            }
        }catch (Exception e){
            return e.getMessage();
        }
    }

    public List<Customer> getAll(){
        try {
            return customerRepo.findAll();
        }catch (Exception e){
            return null;
        }
    }

    public Customer getByID(String ID){
        try {
            Optional<Customer> cus = customerRepo.findById(ID);
            return cus.orElse(null);
        }catch (Exception e){
            return null;
        }
    }

    public String delete(String ID){
        try {
            Optional<Customer> cus = customerRepo.findById(ID);
            if(cus.isPresent()){
                customerRepo.deleteById(ID);
                return "SUCCESS";
            }else return "Not Found";
        }catch (Exception e){
            return e.getMessage();
        }
    }

    public String update(String ID, Customer newCustomer){
        try {
            Optional<Customer> cus = customerRepo.findById(ID);
            if(cus.isPresent()){
                if(cus.get().getPhoneNumber().equals(newCustomer.getPhoneNumber())){
                    customerRepo.save(newCustomer);
                    return "SUCCESS";
                }else
                    return "FAIL";
            }else
                return "Not Found";
        }catch (Exception e){
            return e.getMessage();
        }
    }

    public List<Order> getCustomerOrders(String customerID){
        try {
            Optional<Customer> cus = customerRepo.findById(customerID);
            if(cus.isPresent()){
                return cus.get().getOrders();
            }else
                return null;
        }catch (Exception e){
            return null;
        }
    }
}
