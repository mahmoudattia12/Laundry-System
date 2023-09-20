package com.example.LaundrySystem.Controller.ServiceProvider;

import com.example.LaundrySystem.Entities.Customer;
import com.example.LaundrySystem.Entities.Laundry;
import com.example.LaundrySystem.Entities.Order;
import com.example.LaundrySystem.Entities.ReceivedOrder;
import com.example.LaundrySystem.Repositories.CustomerRepository;
import com.example.LaundrySystem.Repositories.LaundryRepository;
import com.example.LaundrySystem.Repositories.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
//    @Transactional
public class OrderServices {
    @Autowired
    OrderRepository orderRepo;
    @Autowired
    LaundryRepository laundryRepo;
    @Autowired
    CustomerRepository customerRepo;

    public String add(ReceivedOrder receivedOrder) {
        try {
            Optional<Laundry> checkLaundry = laundryRepo.findById(receivedOrder.getLaundryName());
            if (checkLaundry.isPresent()) {
                Optional<Customer> checkCustomer = customerRepo.findById(receivedOrder.getCustomerPhone());
                if (checkCustomer.isPresent()) {
                    Order order = new Order();
                    order.setLaundry(checkLaundry.get());
                    order.setCustomer(checkCustomer.get());
                    LocalDateTime startDate = LocalDateTime.parse(receivedOrder.getStartDateTime()), endDate = LocalDateTime.parse(receivedOrder.getEndDateTime());
                    order.setStartDate(startDate); order.setEndDate(endDate);
                    order.setCurrState(receivedOrder.getCurrState());
                    if(receivedOrder.getAlternatePhone() != null) order.setAlternatePhone(receivedOrder.getAlternatePhone());
                    if(receivedOrder.getIsDelivery().equals("true")) order.setDelivery(true); else order.setDelivery(false);
                    if(receivedOrder.getIsPaid().equals("true")) order.setPaid(true); else order.setPaid(false);
                    orderRepo.save(order);
                    return Integer.toString(order.getID());
                } else {
                    return "Customer Not Found";
                }
            } else {
                return "Laundry Not Found";
            }
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String delete(int ID) {
        try {
            Optional<Order> ord = orderRepo.findById(ID);
            if (ord.isPresent()) {
                orderRepo.deleteById(ID);
                return "SUCCESS";
            } else
                return "Not Found";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String update(int ID, Order newOrder) {
        try {
            Optional<Order> ord = orderRepo.findById(ID);
            if (ord.isPresent()) {
                String oldPhone = ord.get().getCustomer().getPhoneNumber(), newPhone = newOrder.getCustomer().getPhoneNumber();
                if (newOrder.getID() == ID && oldPhone.equals(newPhone)) {
                    orderRepo.save(newOrder);
                    return "SUCCESS";
                } else
                    return "FAIL";
            } else
                return "Not Found";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public List<Order> getAll() {
        try {
            return orderRepo.findAll();
        } catch (Exception e) {
            return null;
        }
    }

    public Order getByID(int ID) {
        try {
            Optional<Order> ord = orderRepo.findById(ID);
            return ord.orElse(null);
        } catch (Exception e) {
            return null;
        }
    }
}
