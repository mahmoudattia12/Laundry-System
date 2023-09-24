package com.example.LaundrySystem.Controller.ServiceProvider;

import com.example.LaundrySystem.Entities.Order;
import com.example.LaundrySystem.Entities.OrderItemPair;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderAdapter {
    public List<OrderItemPair> convert (List<Order> orders){
        List<OrderItemPair> orderItemPairs = new ArrayList<>();
        for(Order order : orders){
            orderItemPairs.add(new OrderItemPair(order, order.getItems()));
        }
        return orderItemPairs;
    }
}
