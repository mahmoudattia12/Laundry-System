package com.example.LaundrySystem.Repositories;

import com.example.LaundrySystem.Entities.Customer;
import com.example.LaundrySystem.Entities.Laundry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LaundryRepository extends JpaRepository<Laundry, String> {
}
