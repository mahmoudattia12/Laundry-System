package com.example.LaundrySystem.Repositories;

import com.example.LaundrySystem.Entities.Employee;
import com.example.LaundrySystem.Entities.EmployeeTask;
import com.example.LaundrySystem.Entities.Order;
import com.example.LaundrySystem.Entities.TaskPrimaryKey;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeTaskRepository extends JpaRepository<EmployeeTask, TaskPrimaryKey> {
    @Transactional
    void deleteByEmployee(Employee employee);
}
