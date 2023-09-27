package com.example.LaundrySystem.Repositories;

import com.example.LaundrySystem.Entities.Employee;
import com.example.LaundrySystem.Entities.EmployeeHoliday;
import com.example.LaundrySystem.Entities.HolidayPrimaryKey;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeHolidayRepository extends JpaRepository<EmployeeHoliday, HolidayPrimaryKey> {
    @Transactional
    void deleteByEmployee(Employee employee);
}
