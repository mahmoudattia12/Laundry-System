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
    //    @Query("SELECT o FROM Order o JOIN FETCH o.items")
//    List<Order> findAllWithItems();
//    @Query("SELECT DISTINCT o FROM Order o LEFT JOIN FETCH o.items")
//    List<Order> findAllWithItems();
//    @Query("SELECT new com.example.LaundrySystem.Repositories.OrderWithItemsDTO(o.id, o.currState) FROM Order o")
//    List<OrderWithItemsDTO> findAllOrdersWithItems();

//    @Query("SELECT new com.example.LaundrySystem.Entities.OrderItemPair(o, oi) FROM Order o JOIN o.items oi")
//    List<OrderItemPair> findAllOrdersWithItems();

//    //sort
//    List<Order> findAllByOrderByIDAsc();
//
//    List<Order> findAllByOrderByIDDesc();
//
//    List<Order> findAllByOrderByCustomerPhoneNumberAsc();
//
//    List<Order> findAllByOrderByCustomerPhoneNumberDesc();
//
//    List<Order> findAllByOrderByCustomerNameAsc();
//
//    List<Order> findAllByOrderByCustomerNameDesc();
//
//    List<Order> findAllByOrderByAlternatePhoneAsc();
//
//    List<Order> findAllByOrderByAlternatePhoneDesc();
//
//    List<Order> findAllByOrderByCurrStateAsc();
//
//    List<Order> findAllByOrderByCurrStateDesc();
//
//    List<Order> findAllByOrderByStartDateAsc();
//
//    List<Order> findAllByOrderByStartDateDesc();
//
//    List<Order> findAllByOrderByEndDateAsc();
//
//    List<Order> findAllByOrderByEndDateDesc();
//
//    List<Order> findAllByOrderByTotalPriceAsc();
//
//    List<Order> findAllByOrderByTotalPriceDesc();
//
//    //filter
//    @Query("SELECT o FROM Order o WHERE CAST(o.ID AS string) = :ID")
//    List<Order> findByID(@Param("ID") String ID);
//
//    List<Order> findByCurrState(String currState);
//
//    @Query("SELECT o FROM Order o WHERE CAST(o.isDelivery AS string) = :isDelivery")
//    List<Order> findByIsDelivery(@Param("isDelivery") String isDelivery);
//
//    @Query("SELECT o FROM Order o WHERE CAST(o.isPaid AS string) = :isPaid")
//    List<Order> findByIsPaid(@Param("isPaid") String isPaid);
//
//    @Query("SELECT o FROM Order o WHERE CAST(o.startDate AS string) = :startDate")
//    List<Order> findByStartDate(@Param("startDate") String startDate);
//
//    @Query("SELECT o FROM Order o WHERE CAST(o.endDate AS string) = :endDate")
//    List<Order> findByEndDate(@Param("endDate") String endDate);
//
//    //    @Query("SELECT o FROM Order o WHERE CAST(o.totalPrice AS string) = :totalPrice")
////    List<Order> findByTotalPrice(@Param("totalPrice")String totalPrice);
//    List<Order> findByTotalPrice(double totalPrice);
//
//    List<Order> findByAlternatePhone(String alternatePhone);
//
//    List<Order> findByCustomerPhoneNumber(String customerPhoneNumber);
//    List<Order> findByCustomerName(String customerName);
//
//    //search
//    @Query("SELECT o FROM Order o WHERE " +
//            "CAST(o.ID AS string) LIKE %:partialInput% OR " +
//            "o.currState LIKE %:partialInput% OR " +
//            "o.alternatePhone LIKE %:partialInput% OR " +
//            "o.customer.phoneNumber LIKE %:partialInput% OR " +
//            "o.customer.name LIKE %:partialInput% OR " +
//            "CAST(o.totalPrice AS string) LIKE %:partialInput% OR " +
//            "CAST(o.isDelivery AS string) LIKE %:partialInput% OR " +
//            "CAST(o.isPaid AS string) LIKE %:partialInput% OR " +
//            "CAST(o.startDate AS string) LIKE %:partialInput% OR " +
//            "CAST(o.endDate AS string) LIKE %:partialInput%")
//    List<Order> findByAttributesContaining(@Param("partialInput") String partialInput);
//
////    @Query("SELECT o FROM Order o WHERE " +
////            "CAST(o.ID AS string) LIKE %:partialInput% OR " +
////            "o.currState LIKE %:partialInput% OR " +
////            "o.alternatePhone LIKE %:partialInput% OR " +
////            "o.customer.phoneNumber LIKE %:partialInput% OR " +
////            "o.totalPrice LIKE %:number% OR " +
////            "CAST(o.isDelivery AS string) LIKE %:partialInput% OR " +
////            "CAST(o.startDate AS string) LIKE %:partialInput% OR " +
////            "CAST(o.endDate AS string) LIKE %:partialInput%")
////    List<Order> findByAttributesContaining(@Param("partialInput") String partialInput, @Param("number") double number);

    // Sort orders for a specific laundry

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
