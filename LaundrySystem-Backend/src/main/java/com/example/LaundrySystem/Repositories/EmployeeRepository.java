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
    List<Employee> findAllByLaundryNameOrderByUserNameAsc(String laundryName);
    List<Employee> findAllByLaundryNameOrderByUserNameDesc(String laundryName);
    List<Employee> findAllByLaundryNameOrderByEmailAsc(String laundryName);
    List<Employee> findAllByLaundryNameOrderByEmailDesc(String laundryName);
    List<Employee> findAllByLaundryNameOrderByStartShiftTimeAsc(String laundryName);
    List<Employee> findAllByLaundryNameOrderByStartShiftTimeDesc(String laundryName);
    List<Employee> findAllByLaundryNameOrderByEndShiftTimeAsc(String laundryName);
    List<Employee> findAllByLaundryNameOrderByEndShiftTimeDesc(String laundryName);
    List<Employee> findAllByLaundryNameOrderByPhoneNumberAsc(String laundryName);
    List<Employee> findAllByLaundryNameOrderByPhoneNumberDesc(String laundryName);
    List<Employee> findAllByLaundryNameOrderBySalaryAsc(String laundryName);
    List<Employee> findAllByLaundryNameOrderBySalaryDesc(String laundryName);

    //filter
    List<Employee> findByUserNameAndLaundryName(String userName, String laundryName);
    List<Employee> findByEmailAndLaundryName(String email, String laundryName);
    List<Employee> findByPhoneNumberAndLaundryName(String phoneNumber, String laundryName);
//    @Query("SELECT e FROM Employee e WHERE CAST(e.salary AS string) = :salary")
//    List<Employee> findBySalary(@Param("salary") String salary);
    List<Employee> findBySalaryAndLaundryName(Double salary, String laundryName);

    @Query("SELECT e FROM Employee e WHERE e.laundry.name = :laundryName AND CAST(e.isManager AS string) = :isManager")
    List<Employee> findByIsManagerAndLaundryName(@Param("isManager") String isManager, @Param("laundryName") String laundryName);
    @Query("SELECT e FROM Employee e WHERE e.laundry.name = :laundryName AND CAST(e.startShiftTime AS string) = :startShiftTime")
    List<Employee> findByStartShiftTimeAndLaundryName(@Param("startShiftTime") String startShiftTime, @Param("laundryName") String laundryName);
    @Query("SELECT e FROM Employee e WHERE e.laundry.name = :laundryName AND CAST(e.endShiftTime AS string) = :endShiftTime")
    List<Employee> findByEndShiftTimeAndLaundryName(@Param("endShiftTime") String endShiftTime, @Param("laundryName") String laundryName);

    //search
    // Custom query to search for employees by multiple attributes
    @Query("SELECT e FROM Employee e WHERE " +
            "e.laundry.name = :laundryName AND (" +
            "e.userName LIKE %:partialInput% OR " +
            "e.email LIKE %:partialInput% OR " +
            "e.phoneNumber LIKE %:partialInput% OR " +
            "CAST (e.isManager AS string) LIKE %:partialInput% OR " +
            "CAST(e.salary AS string) LIKE %:partialInput% OR " +
            "CAST(e.startShiftTime AS string) LIKE %:partialInput% OR " +
            "CAST(e.endShiftTime AS string) LIKE %:partialInput%)")
    List<Employee> findByAttributesContainingAndLaundryName(@Param("partialInput") String partialInput, @Param("laundryName") String laundryName);
}