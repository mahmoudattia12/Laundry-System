package com.example.LaundrySystem.Controller.Sorting;

import com.example.LaundrySystem.Entities.Order;
import com.example.LaundrySystem.Repositories.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class SortOrder <T extends Comparable<T>> implements ISorterStrategy {
    @Autowired
    OrderRepository orderRepo;
    @Override
    public List<Order> sort(String sortBy, boolean order, String laundryName) {
        try{
            switch (sortBy){
                case "ID":
                    if(order) return orderRepo.findAllByLaundryNameOrderByIDAsc(laundryName); else return orderRepo.findAllByLaundryNameOrderByIDDesc(laundryName);
                case "customerPhone":
                    if(order) return orderRepo.findAllByLaundryNameOrderByCustomerPhoneNumberAsc(laundryName); else return orderRepo.findAllByLaundryNameOrderByCustomerPhoneNumberDesc(laundryName);
                case "customerName":
                    if(order) return orderRepo.findAllByLaundryNameOrderByCustomerNameAsc(laundryName); else return orderRepo.findAllByLaundryNameOrderByCustomerNameDesc(laundryName);
                case "price":
                    if(order) return orderRepo.findAllByLaundryNameOrderByTotalPriceAsc(laundryName); else return orderRepo.findAllByLaundryNameOrderByTotalPriceDesc(laundryName);
                case "alternatePhone":
                    if(order) return orderRepo.findAllByLaundryNameOrderByAlternatePhoneAsc(laundryName); else return orderRepo.findAllByLaundryNameOrderByAlternatePhoneDesc(laundryName);
                case "startDate":
                    if(order) return orderRepo.findAllByLaundryNameOrderByStartDateAsc(laundryName); else return orderRepo.findAllByLaundryNameOrderByStartDateDesc(laundryName);
                case "endDate":
                    if(order) return orderRepo.findAllByLaundryNameOrderByEndDateAsc(laundryName); else return orderRepo.findAllByLaundryNameOrderByEndDateDesc(laundryName);
                case "currState":
                    if(order) return orderRepo.findAllByLaundryNameOrderByCurrStateAsc(laundryName); else return orderRepo.findAllByLaundryNameOrderByCurrStateDesc(laundryName);
                case "isDelivery":
                    if(order) return orderRepo.findAllByLaundryNameOrderByIsDeliveryAsc(laundryName); else return orderRepo.findAllByLaundryNameOrderByIsDeliveryDesc(laundryName);
                case "isPaid":
                    if(order) return orderRepo.findAllByLaundryNameOrderByIsPaidAsc(laundryName); else return orderRepo.findAllByLaundryNameOrderByIsPaidDesc(laundryName);
                default:
                    return null;
            }
        }catch (Exception e){
            return null;
        }
    }
}
