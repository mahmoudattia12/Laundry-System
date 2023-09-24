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
                case "userName" -> empRepo.findByUserName(toMeet);
                case "phoneNumber" -> empRepo.findByPhoneNumber(toMeet);
                case "email" -> empRepo.findByEmail(toMeet);
                case "salary" -> empRepo.findBySalary(Double.parseDouble(toMeet));
                case "startShift" -> empRepo.findByStartShiftTime(toMeet);
                case "endShift" -> empRepo.findByEndShiftTime(toMeet);
                case "isManager" -> empRepo.findByIsManager(toMeet);
                case "search" -> empRepo.findByAttributesContaining(toMeet);
                default -> null;
            };
        }catch (Exception e){
            return null;
        }
    }
}
