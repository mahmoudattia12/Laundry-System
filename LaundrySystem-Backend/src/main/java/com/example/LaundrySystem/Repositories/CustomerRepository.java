package com.example.LaundrySystem.Repositories;

import com.example.LaundrySystem.Entities.Customer;
import com.example.LaundrySystem.Entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository <T extends Comparable<T>> extends JpaRepository<Customer, String> {
    //sort
    List<Customer> findAllByOrderByNameAsc();
    List<Customer> findAllByOrderByNameDesc();
    List<Customer> findAllByOrderByEmailAsc();
    List<Customer> findAllByOrderByEmailDesc();
    List<Customer> findAllByOrderByPhoneNumberAsc();
    List<Customer> findAllByOrderByPhoneNumberDesc();
    List<Customer> findAllByOrderByAddressAsc();
    List<Customer> findAllByOrderByAddressDesc();
    List<Customer> findAllByOrderByTotalPaysAsc();
    List<Customer> findAllByOrderByTotalPaysDesc();

    //filter
    List<Customer> findByPhoneNumber(String phoneNumber);
    List<Customer> findByEmail(String email);
    List<Customer> findByName(String name);
    List<Customer> findByAddress(String address);
    @Query("SELECT c FROM Customer c WHERE CAST(c.isGoldCustomer AS string) = :isGoldCustomer")
    List<Customer> findByIsGoldCustomer(@Param("isGoldCustomer") String isGoldCustomer);
    //@Query("SELECT c FROM Customer c WHERE CAST(c.totalPays AS string) = :totalPays")
    //List<Customer> findByTotalPays(@Param("totalPays") String totalPays);
    List<Customer> findByTotalPays(double totalPays);

    //search
    @Query("SELECT c FROM Customer c WHERE " +
            "c.name LIKE %:partialInput% OR " +
            "c.email LIKE %:partialInput% OR " +
            "c.phoneNumber LIKE %:partialInput% OR " +
            "c.address LIKE %:partialInput% OR " +
            "CAST(c.isGoldCustomer AS string) LIKE %:partialInput% OR " +
            "c.totalPays = :number")
    List<Customer> findByAttributesContaining(@Param("partialInput") String partialInput, @Param("number") double number);
    @Query("SELECT c FROM Customer c WHERE " +
            "c.name LIKE %:partialInput% OR " +
            "c.email LIKE %:partialInput% OR " +
            "c.phoneNumber LIKE %:partialInput% OR " +
            "c.address LIKE %:partialInput% OR " +
            "CAST(c.isGoldCustomer AS string) LIKE %:partialInput% OR " +
            "CAST(c.totalPays AS string) LIKE %:partialInput%")
    List<Customer> findByAttributesContaining(@Param("partialInput") String partialInput);



}