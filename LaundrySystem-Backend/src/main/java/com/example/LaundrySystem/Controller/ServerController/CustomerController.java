package com.example.LaundrySystem.Controller.ServerController;

import com.example.LaundrySystem.Controller.ServiceProvider.CustomerServices;
import com.example.LaundrySystem.Entities.Customer;
import com.example.LaundrySystem.Entities.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    CustomerServices customerServices;

    @PostMapping("/add")
    public String addCustomer(@RequestBody Customer customer, @RequestParam("laundryName") String laundryName){
        return customerServices.add(customer, laundryName);
    }

    @PutMapping("/update/{ID}")
    public String updateCustomer(@PathVariable String ID, @RequestBody Customer newCustomer){
        return customerServices.update(ID, newCustomer);
    }

    @DeleteMapping("/delete/{ID}")
    public String deleteCustomer(@PathVariable String ID){
        return customerServices.delete(ID);
    }

    @GetMapping("/getAll")
    public List<Customer> getAll(){
        return customerServices.getAll();
    }

    @GetMapping("/getByID/{ID}")
    public Customer getByID(@PathVariable String ID){
        return customerServices.getByID(ID);
    }

    @GetMapping("/getCustomerOrders/{customerID}/{laundryName}")
    public List<Order> getCustomerOrders(@PathVariable String ID, @PathVariable String laundryName){
        return customerServices.getCustomerOrders(ID, laundryName);
    }
}
