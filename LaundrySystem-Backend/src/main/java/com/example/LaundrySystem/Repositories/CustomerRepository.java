package com.example.LaundrySystem.Repositories;

import com.example.LaundrySystem.Entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository <T extends Comparable<T>> extends JpaRepository<Customer, String> {
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
}