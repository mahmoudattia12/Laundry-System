package com.example.LaundrySystem.Repositories;

import com.example.LaundrySystem.Entities.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository<T extends Comparable<T>> extends JpaRepository<Order, Integer> {
    List<Order> findAllByLaundryNameOrderByIDAsc(String laundryName);

    List<Order> findAllByLaundryNameOrderByIDDesc(String laundryName);

    List<Order> findAllByLaundryNameOrderByCustomerPhoneNumberAsc(String laundryName);

    List<Order> findAllByLaundryNameOrderByCustomerPhoneNumberDesc(String laundryName);

    List<Order> findAllByLaundryNameOrderByCustomerNameAsc(String laundryName);

    List<Order> findAllByLaundryNameOrderByCustomerNameDesc(String laundryName);

    List<Order> findAllByLaundryNameOrderByAlternatePhoneAsc(String laundryName);

    List<Order> findAllByLaundryNameOrderByAlternatePhoneDesc(String laundryName);

    List<Order> findAllByLaundryNameOrderByCurrStateAsc(String laundryName);

    List<Order> findAllByLaundryNameOrderByCurrStateDesc(String laundryName);

    List<Order> findAllByLaundryNameOrderByStartDateAsc(String laundryName);

    List<Order> findAllByLaundryNameOrderByStartDateDesc(String laundryName);

    List<Order> findAllByLaundryNameOrderByEndDateAsc(String laundryName);

    List<Order> findAllByLaundryNameOrderByEndDateDesc(String laundryName);

    List<Order> findAllByLaundryNameOrderByTotalPriceAsc(String laundryName);

    List<Order> findAllByLaundryNameOrderByTotalPriceDesc(String laundryName);
    List<Order> findAllByLaundryNameOrderByIsDeliveryAsc(String laundryName);

    List<Order> findAllByLaundryNameOrderByIsDeliveryDesc(String laundryName);
    List<Order> findAllByLaundryNameOrderByIsPaidAsc(String laundryName);

    List<Order> findAllByLaundryNameOrderByIsPaidDesc(String laundryName);

    // Filter orders for a specific laundry

    @Query("SELECT o FROM Order o WHERE o.laundry.name = :laundryName AND CAST(o.ID AS string) = :ID")
    List<Order> findByIDAndLaundryName(@Param("ID") String ID, @Param("laundryName") String laundryName);

    List<Order> findByCurrStateAndLaundryName(String currState, String laundryName);

    @Query("SELECT o FROM Order o WHERE o.laundry.name = :laundryName AND CAST(o.isDelivery AS string) = :isDelivery")
    List<Order> findByIsDeliveryAndLaundryName(@Param("isDelivery") String isDelivery, @Param("laundryName") String laundryName);

    @Query("SELECT o FROM Order o WHERE o.laundry.name = :laundryName AND CAST(o.isPaid AS string) = :isPaid")
    List<Order> findByIsPaidAndLaundryName(@Param("isPaid") String isPaid, @Param("laundryName") String laundryName);

    @Query("SELECT o FROM Order o WHERE o.laundry.name = :laundryName AND CAST(o.startDate AS string) = :startDate")
    List<Order> findByStartDateAndLaundryName(@Param("startDate") String startDate, @Param("laundryName") String laundryName);

    @Query("SELECT o FROM Order o WHERE o.laundry.name = :laundryName AND CAST(o.endDate AS string) = :endDate")
    List<Order> findByEndDateAndLaundryName(@Param("endDate") String endDate, @Param("laundryName") String laundryName);

    List<Order> findByTotalPriceAndLaundryName(double totalPrice, String laundryName);

    List<Order> findByAlternatePhoneAndLaundryName(String alternatePhone, String laundryName);

    List<Order> findByCustomerPhoneNumberAndLaundryName(String customerPhoneNumber, String laundryName);

    List<Order> findByCustomerNameAndLaundryName(String customerName, String laundryName);

    // Search orders by attributes and laundry name

    @Query("SELECT o FROM Order o WHERE " +
            "o.laundry.name = :laundryName AND (" +
            "CAST(o.ID AS string) LIKE %:partialInput% OR " +
            "o.currState LIKE %:partialInput% OR " +
            "o.alternatePhone LIKE %:partialInput% OR " +
            "o.customer.phoneNumber LIKE %:partialInput% OR " +
            "o.customer.name LIKE %:partialInput% OR " +
            "CAST(o.totalPrice AS string) LIKE %:partialInput% OR " +
            "CAST(o.isDelivery AS string) LIKE %:partialInput% OR " +
            "CAST(o.isPaid AS string) LIKE %:partialInput% OR " +
            "CAST(o.startDate AS string) LIKE %:partialInput% OR " +
            "CAST(o.endDate AS string) LIKE %:partialInput%)")
    List<Order> findByAttributesContainingAndLaundryName(
            @Param("partialInput") String partialInput,
            @Param("laundryName") String laundryName
    );
}
