package com.example.LaundrySystem.Controller.Filter;

import com.example.LaundrySystem.Entities.Employee;
import com.example.LaundrySystem.Repositories.EmployeeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class FilterEmployees <T extends Comparable<T>> implements IFilter {
    @Autowired
    EmployeeRepository empRepo;
    @Override
    public List<Employee> meetCriteria(String criteria, String toMeet, String laundryName) {
        try {
            return switch (criteria) {
                case "userName" -> empRepo.findByUserNameAndLaundryName(toMeet, laundryName);
                case "phoneNumber" -> empRepo.findByPhoneNumberAndLaundryName(toMeet, laundryName);
                case "email" -> empRepo.findByEmailAndLaundryName(toMeet, laundryName);
                case "salary" -> empRepo.findBySalaryAndLaundryName(Double.parseDouble(toMeet), laundryName);
                case "startShift" -> empRepo.findByStartShiftTimeAndLaundryName(toMeet, laundryName);
                case "endShift" -> empRepo.findByEndShiftTimeAndLaundryName(toMeet, laundryName);
                case "isManager" -> empRepo.findByIsManagerAndLaundryName(toMeet, laundryName);
                case "search" -> empRepo.findByAttributesContainingAndLaundryName(toMeet, laundryName);
                default -> null;
            };
        }catch (Exception e){
            return null;
        }
    }
}
