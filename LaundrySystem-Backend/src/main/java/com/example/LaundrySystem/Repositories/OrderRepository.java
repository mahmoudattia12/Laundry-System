package com.example.LaundrySystem.Repositories;

import com.example.LaundrySystem.Entities.Customer;
import com.example.LaundrySystem.Entities.Employee;
import com.example.LaundrySystem.Entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository <T extends Comparable<T>> extends JpaRepository<Order, Integer> {
    //sort
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

    //filter
    @Query("SELECT o FROM Order o WHERE CAST(o.ID AS string) = :ID")
    List<Order> findByID(@Param("ID")String ID);
    List<Order> findByCurrState(String currState);
    @Query("SELECT o FROM Order o WHERE CAST(o.isDelivery AS string) = :isDelivery")
    List<Order> findByIsDelivery(@Param("isDelivery")String isDelivery);
    @Query("SELECT o FROM Order o WHERE CAST(o.startDate AS string) = :startDate")
    List<Order> findByStartDate(@Param("startDate")String startDate);
    @Query("SELECT o FROM Order o WHERE CAST(o.endDate AS string) = :endDate")
    List<Order> findByEndDate(@Param("endDate")String endDate);
    @Query("SELECT o FROM Order o WHERE CAST(o.totalPrice AS string) = :totalPrice")
    List<Order> findByTotalPrice(@Param("totalPrice")String totalPrice);
    List<Order> findByAlternatePhone(String alternatePhone);
    List<Order> findByCustomerPhoneNumber(String customerPhoneNumber);

    //search
    @Query("SELECT o FROM Order o WHERE " +
            "CAST(o.ID AS string) LIKE %:partialInput% OR " +
            "o.currState LIKE %:partialInput% OR " +
            "o.alternatePhone LIKE %:partialInput% OR " +
            "o.customer.phoneNumber LIKE %:partialInput% OR " +
            "CAST(o.totalPrice AS string) LIKE %:partialInput% OR " +
            "CAST(o.startDate AS string) LIKE %:partialInput% OR " +
            "CAST(o.endDate AS string) LIKE %:partialInput%")
    List<Order> findByAttributesContaining(@Param("partialInput") String partialInput);
}
