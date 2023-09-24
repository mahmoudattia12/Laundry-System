package com.example.LaundrySystem.Controller.Filter;

import com.example.LaundrySystem.Entities.Order;
import com.example.LaundrySystem.Repositories.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class FilterOrders<T extends Comparable<T>> implements IFilter{
    @Autowired
    OrderRepository orderRepo;
    @Override
    public List<Order> meetCriteria(String criteria, String toMeet, String laundryName) {
        try {
            return switch (criteria) {
                case "ID" -> orderRepo.findByIDAndLaundryName(toMeet, laundryName);
                case "customerPhone" -> orderRepo.findByCustomerPhoneNumberAndLaundryName(toMeet, laundryName);
                case "price" -> orderRepo.findByTotalPriceAndLaundryName(Double.parseDouble(toMeet), laundryName);
                case "alternatePhone" -> orderRepo.findByAlternatePhoneAndLaundryName(toMeet, laundryName);
                case "startDate" -> orderRepo.findByStartDateAndLaundryName(toMeet, laundryName);
                case "endDate" -> orderRepo.findByEndDateAndLaundryName(toMeet, laundryName);
                case "currState" -> orderRepo.findByCurrStateAndLaundryName(toMeet, laundryName);
                case "isDelivery" -> orderRepo.findByIsDeliveryAndLaundryName(toMeet, laundryName);
                case "isPaid" -> orderRepo.findByIsPaidAndLaundryName(toMeet, laundryName);
                case "customerName" -> orderRepo.findByCustomerNameAndLaundryName(toMeet, laundryName);
                case "search" -> orderRepo.findByAttributesContainingAndLaundryName(toMeet, laundryName);
                default -> null;
            };
        }catch (Exception e){
            return null;
        }
    }
}
