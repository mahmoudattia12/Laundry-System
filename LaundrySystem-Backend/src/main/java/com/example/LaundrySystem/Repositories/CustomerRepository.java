package com.example.LaundrySystem.Repositories;

import com.example.LaundrySystem.Entities.Customer;
import com.example.LaundrySystem.Entities.Employee;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository<T extends Comparable<T>> extends JpaRepository<Customer, String> {
//    //sort
//    List<Customer> findAllByLaundryNameOrderByNameAsc(String laundryName);
//    List<Customer> findAllByLaundryNameOrderByNameDesc(String laundryName);
//    List<Customer> findAllByLaundryNameOrderByEmailAsc(String laundryName);
//    List<Customer> findAllByLaundryNameOrderByEmailDesc(String laundryName);
//    List<Customer> findAllByLaundryNameOrderByPhoneNumberAsc(String laundryName);
//    List<Customer> findAllByLaundryNameOrderByPhoneNumberDesc(String laundryName);
//    List<Customer> findAllByLaundryNameOrderByAddressAsc(String laundryName);
//    List<Customer> findAllByLaundryNameOrderByAddressDesc(String laundryName);
////    List<Customer> findAllByOrderByTotalPaysAsc();
////    List<Customer> findAllByOrderByTotalPaysDesc();
//
//    //filter
//    List<Customer> findByPhoneNumberAndLaundryName(String phoneNumber, String laundryName);
//    List<Customer> findByEmailAndLaundryName(String email, String laundryName);
//    List<Customer> findByNameAndLaundryName(String name, String laundryName);
//    List<Customer> findByAddressAndLaundryName(String address, String laundryName);
////    @Query("SELECT c FROM Customer c WHERE CAST(c.isGoldCustomer AS string) = :isGoldCustomer")
////    List<Customer> findByIsGoldCustomer(@Param("isGoldCustomer") String isGoldCustomer);
////    //@Query("SELECT c FROM Customer c WHERE CAST(c.totalPays AS string) = :totalPays")
////    //List<Customer> findByTotalPays(@Param("totalPays") String totalPays);
////    List<Customer> findByTotalPays(double totalPays);
//
//    //search
////    @Query("SELECT c FROM Customer c WHERE " +
////            "c.name LIKE %:partialInput% OR " +
////            "c.email LIKE %:partialInput% OR " +
////            "c.phoneNumber LIKE %:partialInput% OR " +
////            "c.address LIKE %:partialInput% OR " +
////            "CAST(c.isGoldCustomer AS string) LIKE %:partialInput% OR " +
////            "c.totalPays = :number")
////    List<Customer> findByAttributesContaining(@Param("partialInput") String partialInput, @Param("number") double number);

    List<Customer> findAllByLaundries_Name(String laundryName, Sort sort);

    List<Customer> findByLaundries_NameAndName(String laundryName, String name);

    List<Customer> findByLaundries_NameAndEmail(String laundryName, String email);

    List<Customer> findByLaundries_NameAndPhoneNumber(String laundryName, String phoneNumber);

    List<Customer> findByLaundries_NameAndAddress(String laundryName, String address);
//    @Query("SELECT c FROM Customer c WHERE " +
//            "c.laundry.name = :laundryName AND (" +
//            "c.name LIKE %:partialInput% OR " +
//            "c.email LIKE %:partialInput% OR " +
//            "c.phoneNumber LIKE %:partialInput% OR " +
//            "c.address LIKE %:partialInput%)" )
//
//    List<Customer> findByAttributesContainingAndLaundryName(@Param("laundryName") String laundryName, @Param("partialInput") String partialInput);

// Search customers by partial input in name, email, or phone number for a given laundryName
    @Query("SELECT c FROM Customer c JOIN c.laundries l WHERE l.name = :laundryName AND " +
            "(c.name LIKE %:partialInput% OR c.address LIKE %:partialInput% OR c.email LIKE %:partialInput% OR c.phoneNumber LIKE %:partialInput%)")
    List<Customer> findByAttributesContainingAndLaundryName(@Param("laundryName") String laundryName, @Param("partialInput") String partialInput);


}