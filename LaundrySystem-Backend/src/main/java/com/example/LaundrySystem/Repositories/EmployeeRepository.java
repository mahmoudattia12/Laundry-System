package com.example.LaundrySystem.Repositories;

import com.example.LaundrySystem.Entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository <T extends Comparable<T>> extends JpaRepository<Employee, String> {

    List<Employee> findAllByOrderByUserNameAsc();
    List<Employee> findAllByOrderByUserNameDesc();
    List<Employee> findAllByOrderByEmailAsc();
    List<Employee> findAllByOrderByEmailDesc();
    List<Employee> findAllByOrderByStartShiftTimeAsc();
    List<Employee> findAllByOrderByStartShiftTimeDesc();
    List<Employee> findAllByOrderByEndShiftTimeAsc();
    List<Employee> findAllByOrderByEndShiftTimeDesc();
    List<Employee> findAllByOrderByPhoneNumberAsc();
    List<Employee> findAllByOrderByPhoneNumberDesc();
    List<Employee> findAllByOrderBySalaryAsc();
    List<Employee> findAllByOrderBySalaryDesc();
}