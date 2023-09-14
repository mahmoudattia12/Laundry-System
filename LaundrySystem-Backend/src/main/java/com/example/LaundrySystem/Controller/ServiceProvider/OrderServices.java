package com.example.LaundrySystem.Controller.ServiceProvider;

import com.example.LaundrySystem.Entities.Customer;
import com.example.LaundrySystem.Entities.Laundry;
import com.example.LaundrySystem.Entities.Order;
import com.example.LaundrySystem.Repositories.LaundryRepository;
import com.example.LaundrySystem.Repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServices {
    @Autowired
    OrderRepository orderRepo;
    @Autowired
    LaundryRepository laundryRepo;

    public String add(Order order){
        try {
            Optional<Order> ord = orderRepo.findById(order.getID());
            if(ord.isPresent()){
                return "Already Exist";
            }else{
                Optional<Laundry> checkLaundry = laundryRepo.findById(order.getLaundry().getName());
                if(checkLaundry.isPresent()){
                    order.setLaundry(checkLaundry.get());
                    orderRepo.save(order);
                    return Integer.toString(order.getID());
                }else{
                    return "Laundry Not Found";
                }
            }
        }catch (Exception e){
            return e.getMessage();
        }
    }

    public String delete(int ID){
        try {
            Optional<Order> ord = orderRepo.findById(ID);
            if(ord.isPresent()){
                orderRepo.deleteById(ID);
                return "SUCCESS";
            }else
                return "Not Found";
        }catch (Exception e){
            return e.getMessage();
        }
    }

    public String update(int ID, Order newOrder){
        try {
            Optional<Order> ord = orderRepo.findById(ID);
            if(ord.isPresent()){
                String oldPhone = ord.get().getCustomer().getPhoneNumber(), newPhone = newOrder.getCustomer().getPhoneNumber();
                if(newOrder.getID() == ID && oldPhone.equals(newPhone)){
                    orderRepo.save(newOrder);
                    return "SUCCESS";
                }else
                    return "FAIL";
            }else
                return "Not Found";
        }catch (Exception e){
            return e.getMessage();
        }
    }
    public List<Order> getAll(){
        try {
            return orderRepo.findAll();
        }catch (Exception e){
            return null;
        }
    }

    public Order getByID(int ID){
        try {
            Optional<Order> ord = orderRepo.findById(ID);
            return ord.orElse(null);
        }catch (Exception e){
            return null;
        }
    }
}
