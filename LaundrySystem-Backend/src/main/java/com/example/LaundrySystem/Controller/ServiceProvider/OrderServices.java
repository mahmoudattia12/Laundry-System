package com.example.LaundrySystem.Controller.ServiceProvider;
import org.springframework.transaction.annotation.Transactional;

import com.example.LaundrySystem.Entities.*;
import com.example.LaundrySystem.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServices <T extends Comparable<T>> {
    @Autowired
    OrderRepository orderRepo;
    @Autowired
    LaundryRepository laundryRepo;
    @Autowired
    CustomerRepository customerRepo;
    @Autowired
    OrderNoteRepository noteRepo;
    @Autowired
    OrderItemRepository itemRepo;
    public <T> T add(ReceivedOrder receivedOrder) {
        try {
            Optional<Laundry> checkLaundry = laundryRepo.findById(receivedOrder.getLaundryName());
            if (checkLaundry.isPresent()) {
                Optional<Customer> checkCustomer = customerRepo.findById(receivedOrder.getCustomerPhone());
                if (checkCustomer.isPresent() && checkLaundry.get().getCustomers().contains(checkCustomer.get())) {
                    Order order = new Order();
                    order.setLaundry(checkLaundry.get());
                    order.setCustomer(checkCustomer.get());
                    LocalDateTime startDate = LocalDateTime.parse(receivedOrder.getStartDateTime()), endDate = LocalDateTime.parse(receivedOrder.getEndDateTime());
                    order.setStartDate(startDate);
                    order.setEndDate(endDate);
                    order.setCurrState(receivedOrder.getCurrState());
                    if (receivedOrder.getAlternatePhone() != null)
                        order.setAlternatePhone(receivedOrder.getAlternatePhone());
                    if (receivedOrder.getIsDelivery().equals("true")) order.setDelivery(true);
                    else order.setDelivery(false);
                    if (receivedOrder.getIsPaid().equals("true")) order.setPaid(true);
                    else order.setPaid(false);
                    orderRepo.save(order);
                    return (T) order;
                } else {
                    return (T) "Customer Not Found";
                }
            } else {
                return (T) "Laundry Not Found";
            }
        } catch (Exception e) {
            return (T) e.getMessage();
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

    public <T> T update(int ID, ReceivedOrder newOrder) {
        try {
            Optional<Laundry> checkLaundry = laundryRepo.findById(newOrder.getLaundryName());
            if (checkLaundry.isPresent()) {
                Optional<Customer> checkCustomer = customerRepo.findById(newOrder.getCustomerPhone());
                if (checkCustomer.isPresent() && checkLaundry.get().getCustomers().contains(checkCustomer.get())) {
                    Optional<Order> ord = orderRepo.findById(ID);
                    if (ord.isPresent()) {
                        newOrder.setID(ID);
                        String oldPhone = ord.get().getCustomer().getPhoneNumber(), newPhone = newOrder.getCustomerPhone();
                        if (newOrder.getID() == ID && oldPhone.equals(newPhone)) {
                            Order order = ord.get();
                            order.setLaundry(checkLaundry.get());
                            order.setCustomer(checkCustomer.get());
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                            LocalDateTime startDate, endDate;
                            if(newOrder.getStartDateTime().contains("T")) startDate = LocalDateTime.parse(newOrder.getStartDateTime());
                            else startDate = LocalDateTime.parse(newOrder.getStartDateTime(), formatter);

                            if(newOrder.getEndDateTime().contains("T")) endDate = LocalDateTime.parse(newOrder.getEndDateTime());
                            else endDate = LocalDateTime.parse(newOrder.getEndDateTime(), formatter);
                            order.setStartDate(startDate);
                            order.setEndDate(endDate);
                            order.setCurrState(newOrder.getCurrState());
                            if (newOrder.getAlternatePhone() != null)
                                order.setAlternatePhone(newOrder.getAlternatePhone());
                            if (newOrder.getIsDelivery().equals("true")) order.setDelivery(true);
                            else order.setDelivery(false);
                            if (newOrder.getIsPaid().equals("true")) order.setPaid(true);
                            else order.setPaid(false);
                            //delete past items and notes
                            itemRepo.deleteByOrder(order);
                            noteRepo.deleteByOrder(order);
                            List<OrderItem> orderItems = newOrder.getItems();
                            for(OrderItem orderItem : orderItems){
                                orderItem.setOrder(order);
                            }
                            order.setItems(orderItems);
                            List<OrderNote> orderNotes = new ArrayList<>();
                            for(String note : newOrder.getNotes()){
                                NotePrimaryKey notePK = new NotePrimaryKey(order, note);
                                Optional<OrderNote> checkNote = noteRepo.findById(notePK);
                                if(!checkNote.isPresent()){
                                    orderNotes.add(new OrderNote(order, note));
                                }
                            }
                            order.setNotes(orderNotes);
                            orderRepo.save(order);
                            return (T) new OrderItemPair(order, order.getItems());
                        } else
                            return (T) "FAIL";
                    } else
                        return (T) "Not Found";
                } else {
                    return (T) "Customer Not Found";
                }

            } else {
                return (T) "Laundry Not Found";
            }

        } catch (Exception e) {
            return (T) ("Error: Failed to update \nmay be because you can't duplicate the same (type + service Category) for the orderItems\n"
                                +"So accumulate them in one item"
                                + e.getMessage());
        }
    }

    public List<OrderItemPair> getAll(String laundryName) {
        try {

            Optional<Laundry> checkLaundry = laundryRepo.findById(laundryName);
            if (checkLaundry.isPresent()) {

                List<OrderItemPair> ordersItems = new ArrayList<>();
                List<Order> orders = checkLaundry.get().getOrders();
                for (Order order : orders) {
                    ordersItems.add(new OrderItemPair(order, order.getItems()));
                }
                return ordersItems;
            } else {
                return null;
            }
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
