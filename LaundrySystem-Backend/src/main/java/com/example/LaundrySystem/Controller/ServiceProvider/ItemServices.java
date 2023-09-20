package com.example.LaundrySystem.Controller.ServiceProvider;

import com.example.LaundrySystem.Entities.ItemPrimaryKey;
import com.example.LaundrySystem.Entities.Order;
import com.example.LaundrySystem.Entities.OrderItem;
import com.example.LaundrySystem.Repositories.OrderItemRepository;
import com.example.LaundrySystem.Repositories.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ItemServices {
    @Autowired
    OrderItemRepository itemRepo;
    @Autowired
    OrderRepository orderRepo;

    public String addItems(OrderItem[] orderItems, int orderID){
        try {
            Optional<Order> checkOrder = orderRepo.findById(orderID);
            if(checkOrder.isPresent()){
                Order order = checkOrder.get();
                String response = "SUCCESS";
                for(OrderItem item : orderItems){
                    ItemPrimaryKey itemPK = new ItemPrimaryKey(order, item.getType(), item.getServiceCategory());
                    Optional<OrderItem> checkItem = itemRepo.findById(itemPK);
                    if(!checkItem.isPresent()){
                        System.out.println("item ok");
                        item.setOrder(order);
                        itemRepo.save(item);
                    }else{
                        response = "SUCCESS.\nBUT for Items with duplicated (type & service), only the first one was submitted.\n"
                                    +"You can't submit more than one item with same (type & service).\n"
                                    +"now you can edit the order.";
                        System.out.println(response);
                    }
                }
                return response;
            }else{
                System.out.println("Order Not Found");
                return "Order Not Found";
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            return e.getMessage();
        }
    }

    public String update(int orderID, String type, String service, OrderItem newOrderItem){
        try {
            Optional<Order> checkOrder = orderRepo.findById(orderID);
            if(checkOrder.isPresent()) {
                ItemPrimaryKey itemPK = new ItemPrimaryKey(checkOrder.get(), type, service);
                Optional<OrderItem> checkItem = itemRepo.findById(itemPK);
                if (checkItem.isPresent()) {
                    if (newOrderItem.getPK().equals(itemPK)) {
                        itemRepo.save(newOrderItem);
                        return "SUCCESS";
                    } else
                        return "FAIL";
                } else
                    return "NOT FOUND";
            }else{
                return "Order Not Found";
            }
        }catch (Exception e){
            return e.getMessage();
        }
    }

    public String delete(int orderID, String type, String service){
        try {
            Optional<Order> checkOrder = orderRepo.findById(orderID);
            if(checkOrder.isPresent()) {
                ItemPrimaryKey itemPK = new ItemPrimaryKey(checkOrder.get(), type, service);
                Optional<OrderItem> checkItem = itemRepo.findById(itemPK);
                if (checkItem.isPresent()) {
                    itemRepo.delete(checkItem.get());
                    return "SUCCESS";
                } else
                    return "NOT FOUND";
            }else{
                return "Order Not Found";
            }
        }catch (Exception e){
            return e.getMessage();
        }
    }

    public List<OrderItem> getAll(){
        try{
            return itemRepo.findAll();
        } catch (Exception e){
            return null;
        }
    }

    public List<OrderItem> getByID(int orderID){
        try {
            Optional<Order> checkOrder = orderRepo.findById(orderID);
            if(checkOrder.isPresent()){
                return checkOrder.get().getItems();
            }else
                return null;
        } catch (Exception e){
            return null;
        }
    }


}
