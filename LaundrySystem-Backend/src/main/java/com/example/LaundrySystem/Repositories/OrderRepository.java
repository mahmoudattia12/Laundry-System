package com.example.LaundrySystem.Repositories;

import com.example.LaundrySystem.Entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository <T extends Comparable<T>> extends JpaRepository<Order, Integer> {
    List<Order> findAllByOrderByIDAsc();
    List<Order> findAllByOrderByIDDesc();
    List<Order> findAllByOrderByCustomerPhoneNumberAsc();
    List<Order> findAllByOrderByCustomerPhoneNumberDesc();
    List<Order> findAllByOrderByAlternatePhoneAsc();
    List<Order> findAllByOrderByAlternatePhoneDesc();
    List<Order> findAllByOrderByCurrStateAsc();
    List<Order> findAllByOrderByCurrStateDesc();
    List<Order> findAllByOrderByStartDateAsc();
    List<Order> findAllByOrderByStartDateDesc();
    List<Order> findAllByOrderByEndDateAsc();
    List<Order> findAllByOrderByEndDateDesc();
    List<Order> findAllByOrderByTotalPriceAsc();
    List<Order> findAllByOrderByTotalPriceDesc();
}
