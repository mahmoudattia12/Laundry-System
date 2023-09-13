package com.example.LaundrySystem.Controller.Filter;

import com.example.LaundrySystem.Entities.Order;
import com.example.LaundrySystem.Repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilterOrders<T extends Comparable<T>> implements IFilter{
    @Autowired
    OrderRepository orderRepo;
    @Override
    public List<Order> meetCriteria(String criteria, String toMeet) {
        return switch (criteria) {
            case "ID" -> orderRepo.findByID(toMeet);
            case "customerPhone" -> orderRepo.findByCustomerPhoneNumber(toMeet);
            case "price" -> orderRepo.findByTotalPrice(Double.parseDouble(toMeet));
            case "alternatePhone" -> orderRepo.findByAlternatePhone(toMeet);
            case "startDate" -> orderRepo.findByStartDate(toMeet);
            case "endDate" -> orderRepo.findByEndDate(toMeet);
            case "currState" -> orderRepo.findByCurrState(toMeet);
            case "isDelivery" -> orderRepo.findByIsDelivery(toMeet);
            case "search" -> orderRepo.findByAttributesContaining(toMeet);
            default -> null;
        };
    }
}
