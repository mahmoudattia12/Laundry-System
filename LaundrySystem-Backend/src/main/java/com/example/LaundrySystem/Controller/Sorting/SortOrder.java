package com.example.LaundrySystem.Controller.Sorting;

import com.example.LaundrySystem.Entities.Order;
import com.example.LaundrySystem.Repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SortOrder <T extends Comparable<T>> implements ISorterStrategy {
    @Autowired
    OrderRepository orderRepo;
    @Override
    public List<T> sort(String sortBy, boolean order) {
        switch (sortBy){
            case "ID":
                if(order) return orderRepo.findAllByOrderByIDAsc(); else return orderRepo.findAllByOrderByIDDesc();
            case "customerPhone":
                if(order) return orderRepo.findAllByOrderByCustomerPhoneNumberAsc(); else return orderRepo.findAllByOrderByCustomerPhoneNumberDesc();
            case "price":
                if(order) return orderRepo.findAllByOrderByTotalPriceAsc(); else return orderRepo.findAllByOrderByTotalPriceDesc();
            case "alternatePhone":
                if(order) return orderRepo.findAllByOrderByAlternatePhoneAsc(); else return orderRepo.findAllByOrderByAlternatePhoneDesc();
            case "startDate":
                if(order) return orderRepo.findAllByOrderByStartDateAsc(); else return orderRepo.findAllByOrderByStartDateDesc();
            case "endDate":
                if(order) return orderRepo.findAllByOrderByEndDateAsc(); else return orderRepo.findAllByOrderByEndDateDesc();
            case "currState":
                if(order) return orderRepo.findAllByOrderByCurrStateAsc(); else return orderRepo.findAllByOrderByCurrStateDesc();
            default:
                return null;
        }
    }
}
