package com.example.LaundrySystem.Controller.ServiceProvider;

import com.example.LaundrySystem.Entities.Customer;
import com.example.LaundrySystem.Entities.Order;
import com.example.LaundrySystem.Entities.ToSendCustomer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerAdapter {

    public List<ToSendCustomer> convert(List<Customer> customers, String laundryName){
        List<ToSendCustomer> toSendCustomers = new ArrayList<>();
        for(Customer customer : customers){
            double totalPays = 0;
            for(Order order : customer.getOrders()){
                if(order.getLaundry().getName().equals(laundryName))
                    totalPays += order.getTotalPrice();
            }
            toSendCustomers.add(new ToSendCustomer(customer.getPhoneNumber(), customer.getName(), customer.getEmail(), customer.getAddress(), totalPays, totalPays >= 500));
        }
        return toSendCustomers;
    }
}
