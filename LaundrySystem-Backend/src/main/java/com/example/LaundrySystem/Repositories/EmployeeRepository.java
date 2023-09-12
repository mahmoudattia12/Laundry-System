package com.example.LaundrySystem.Repositories;

import com.example.LaundrySystem.Entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;

@Repository
public interface EmployeeRepository <T extends Comparable<T>> extends JpaRepository<Employee, String> {
    //sorting
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

    //filter
    List<Employee> findByUserName(String userName);
    List<Employee> findByEmail(String email);
    List<Employee> findByPhoneNumber(String phoneNumber);
    @Query("SELECT e FROM Employee e WHERE CAST(e.isManager AS string) = :isManager")
    List<Employee> findByIsManager(@Param("isManager") String isManager);
    @Query("SELECT e FROM Employee e WHERE CAST(e.salary AS string) = :salary")
    List<Employee> findBySalary(@Param("salary") String salary);
    @Query("SELECT e FROM Employee e WHERE CAST(e.startShiftTime AS string) = :startShiftTime")
    List<Employee> findByStartShiftTime(@Param("startShiftTime") String startShiftTime);
    @Query("SELECT e FROM Employee e WHERE CAST(e.endShiftTime AS string) = :endShiftTime")
    List<Employee> findByEndShiftTime(@Param("endShiftTime") String endShiftTime);

    //search
    // Custom query to search for employees by multiple attributes
    @Query("SELECT e FROM Employee e WHERE " +
            "e.userName LIKE %:partialInput% OR " +
            "e.email LIKE %:partialInput% OR " +
            "e.phoneNumber LIKE %:partialInput% OR " +
            "CAST(e.salary AS string) LIKE %:partialInput% OR " +
            "CAST(e.startShiftTime AS string) LIKE %:partialInput% OR " +
            "CAST(e.endShiftTime AS string) LIKE %:partialInput%")
    List<Employee> findByAttributesContaining(@Param("partialInput") String partialInput);
}