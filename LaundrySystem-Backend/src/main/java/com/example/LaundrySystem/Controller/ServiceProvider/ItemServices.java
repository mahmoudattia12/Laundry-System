package com.example.LaundrySystem.Controller.ServiceProvider;

import com.example.LaundrySystem.Entities.ItemPrimaryKey;
import com.example.LaundrySystem.Entities.Order;
import com.example.LaundrySystem.Entities.OrderItem;
import com.example.LaundrySystem.Repositories.OrderItemRepository;
import com.example.LaundrySystem.Repositories.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
//@Transactional
public class ItemServices <T extends Comparable<T>>  {
    @Autowired
    OrderItemRepository itemRepo;
    @Autowired
    OrderRepository orderRepo;


    @Transactional
    public <T> T addItems(OrderItem[] orderItems, int orderID){
        try {
            Optional<Order> checkOrder = orderRepo.findById(orderID);
            if(checkOrder.isPresent()){
                Order order = checkOrder.get();
                List<OrderItem> temp = new ArrayList<>();
                String response = "SUCCESS";
                for(OrderItem orderItem : orderItems){
                    ItemPrimaryKey itemPK = new ItemPrimaryKey(order, orderItem.getType(), orderItem.getServiceCategory());
                    Optional<OrderItem> checkItem = itemRepo.findById(itemPK);
                    if(!checkItem.isPresent()){
                        System.out.println("item ok");
                        OrderItem item = new OrderItem(order, orderItem.getType(), orderItem.getServiceCategory(), orderItem.getQuantity(), orderItem.getPrice(), orderItem.getDiscount());
                        itemRepo.save(item);
                        temp.add(item);
                    }else{
                        response = "SUCCESS.\nBUT for Items with duplicated (type & service), only the first one was submitted.\n"
                                    +"You can't submit more than one item with same (type & service).\n"
                                    +"now you can edit the order.";
                        //System.out.println(response);
                    }
                }
                //for setting the total order price
                order.getItems().addAll(temp);
                order.setItems(order.getItems());
                System.out.println("from service total price after add is: " + order.getTotalPrice());
                System.out.println("response   :" +response);
                orderRepo.save(order);
                return (T) order.getItems();
            }else{
                return (T) "Order Not Found";
            }
        }catch (Exception e){
            //delete it as the front will consider that the order is not added is this case
            Optional<Order> checkOrder = orderRepo.findById(orderID);
            if(checkOrder.isPresent()){
                orderRepo.deleteById(orderID);
            }
            System.out.println("hi after delete the order as the item has an exception");
            return (T) e.getMessage();
        }
    }

    public String update(int orderID, String type, String service, OrderItem newOrderItem){
        try {
            Optional<Order> checkOrder = orderRepo.findById(orderID);
            if(checkOrder.isPresent()) {
                ItemPrimaryKey itemPK = new ItemPrimaryKey(checkOrder.get(), type, service);
                Optional<OrderItem> checkItem = itemRepo.findById(itemPK);
                if (checkItem.isPresent()) {
                    ItemPrimaryKey newItemPK = new ItemPrimaryKey(newOrderItem.getOrder(), newOrderItem.getType(), newOrderItem.getServiceCategory());
                    if (newItemPK.equals(itemPK)) {
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
