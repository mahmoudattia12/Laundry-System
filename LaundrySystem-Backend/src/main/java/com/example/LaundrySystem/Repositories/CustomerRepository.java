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

    List<Customer> findAllByLaundries_Name(String laundryName, Sort sort);

    List<Customer> findByLaundries_NameAndName(String laundryName, String name);

    List<Customer> findByLaundries_NameAndEmail(String laundryName, String email);

    List<Customer> findByLaundries_NameAndPhoneNumber(String laundryName, String phoneNumber);

    List<Customer> findByLaundries_NameAndAddress(String laundryName, String address);

    @Query("SELECT c FROM Customer c JOIN c.laundries l WHERE l.name = :laundryName AND " +
            "(c.name LIKE %:partialInput% OR c.address LIKE %:partialInput% OR c.email LIKE %:partialInput% OR c.phoneNumber LIKE %:partialInput%)")
    List<Customer> findByAttributesContainingAndLaundryName(@Param("laundryName") String laundryName, @Param("partialInput") String partialInput);


}