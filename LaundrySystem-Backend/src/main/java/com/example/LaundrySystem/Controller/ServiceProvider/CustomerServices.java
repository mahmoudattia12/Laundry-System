package com.example.LaundrySystem.Controller.ServiceProvider;

import com.example.LaundrySystem.Entities.Customer;
import com.example.LaundrySystem.Entities.Laundry;
import com.example.LaundrySystem.Entities.Order;
import com.example.LaundrySystem.Entities.ToSendCustomer;
import com.example.LaundrySystem.Repositories.CustomerRepository;
import com.example.LaundrySystem.Repositories.LaundryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CustomerServices {
    @Autowired
    CustomerRepository customerRepo;
    @Autowired
    LaundryRepository laundryRepo;
    @Autowired
    CustomerAdapter customerAdapter;

    public String add(Customer customer, String laundryName) {
        try {
            Optional<Laundry> checkLaundry = laundryRepo.findById(laundryName);
            if (checkLaundry.isPresent()) {
                Optional<Customer> cus = customerRepo.findById(customer.getPhoneNumber());
                if (cus.isPresent()) {
                    Laundry laundry = checkLaundry.get();
                    Customer customer1 = cus.get();
                    if (customer1.getLaundries().contains(laundry)) {
                        return "Already Exists";
                    } else {
                        customer1.getLaundries().add(laundry);
                        customerRepo.save(customer1);
                        laundry.getCustomers().add(customer1);
                        laundryRepo.save(laundry);
                        return "SUCCESS";
                    }
                } else {
                    Laundry laundry = checkLaundry.get();
                    customer.getLaundries().add(laundry);
                    laundry.getCustomers().add(customer);
                    customerRepo.save(customer);
                    laundryRepo.save(laundry);
                    return "SUCCESS";
                }
            } else {
                return "Laundry Not Found";
            }
        } catch (Exception e) {
            System.out.println("message  " );
            System.out.println(e.getMessage());
            return e.getMessage();
        }
    }

    public List<ToSendCustomer> getAll(String laundryName) {
        try {
            Optional<Laundry> checkLaundry = laundryRepo.findById(laundryName);
            if (checkLaundry.isPresent()) {
                return customerAdapter.convert(checkLaundry.get().getCustomers(), laundryName);
            } else return null;
        } catch (Exception e) {
            return null;
        }
    }


    public Customer getByID(String ID) {
        try {
            Optional<Customer> cus = customerRepo.findById(ID);
            return cus.orElse(null);
        } catch (Exception e) {
            return null;
        }
    }

    public String delete(String ID, String laundryName) {
        try {
            Optional<Customer> cus = customerRepo.findById(ID);
            if (cus.isPresent()) {
                Optional<Laundry> checkLaundry = laundryRepo.findById(laundryName);
                if (checkLaundry.isPresent() && cus.get().getLaundries().contains(checkLaundry.get())) {
                    Customer customer = cus.get();
                    Laundry laundry = checkLaundry.get();
                    if (customer.getLaundries().remove(laundry) && laundry.getCustomers().remove(customer)) {
                        if (customer.getLaundries().size() == 0) {
                            customerRepo.deleteById(ID);
                        } else {
                            customerRepo.save(customer);
                        }
                        laundryRepo.save(laundry);
                        return "SUCCESS";
                    } else {
                        return "Fail";
                    }
                } else {
                    return "Laundry Not Found";
                }
            } else return "Customer Not Found";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String update(String ID, Customer newCustomer) {
        try {
            Optional<Customer> cus = customerRepo.findById(ID);
            if (cus.isPresent()) {
                if (cus.get().getPhoneNumber().equals(ID)) {
                    Customer oldCustomer = cus.get();
                    oldCustomer.setName(newCustomer.getName());
                    oldCustomer.setAddress(newCustomer.getAddress());
                    oldCustomer.setEmail(newCustomer.getEmail());
                    customerRepo.save(oldCustomer);
                    return "SUCCESS";
                } else
                    return "FAIL";
            } else
                return "Not Found";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public List<Order> getCustomerOrders(String customerID, String laundryName) {
        try {
            Optional<Customer> cus = customerRepo.findById(customerID);
            Optional<Laundry> checkLaundry = laundryRepo.findById(laundryName);
            if (cus.isPresent() && checkLaundry.isPresent()) {
                List<Order> withinLaundry = new ArrayList<>();
                for (Order order : cus.get().getOrders()) {
                    if (order.getLaundry().getName().equalsIgnoreCase(laundryName)) {
                        withinLaundry.add(order);
                    }
                }
                return withinLaundry;
            } else
                return null;
        } catch (Exception e) {
            return null;
        }
    }
}
