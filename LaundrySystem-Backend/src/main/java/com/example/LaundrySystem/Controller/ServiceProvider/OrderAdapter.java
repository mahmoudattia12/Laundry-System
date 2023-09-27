package com.example.LaundrySystem.Controller.ServiceProvider;

import com.example.LaundrySystem.Entities.Order;
import com.example.LaundrySystem.Entities.ToSendOrder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderAdapter {
    public List<ToSendOrder> convert (List<Order> orders){
        List<ToSendOrder> orderItemPairs = new ArrayList<>();
        for(Order order : orders){
            orderItemPairs.add(new ToSendOrder(order, order.getItems()));
        }
        return orderItemPairs;
    }
}
