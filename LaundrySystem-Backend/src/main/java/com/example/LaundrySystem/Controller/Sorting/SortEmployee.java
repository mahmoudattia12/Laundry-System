package com.example.LaundrySystem.Controller.Sorting;

import com.example.LaundrySystem.Entities.Employee;
import com.example.LaundrySystem.Repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SortEmployee <T extends Comparable<T>> implements ISorterStrategy {
    @Autowired
    EmployeeRepository empRepo;

    @Override
    public List<T> sort(String sortBy, boolean order) {
        switch (sortBy){
            case "userName":
                if(order) return empRepo.findAllByOrderByUserNameAsc(); else return empRepo.findAllByOrderByUserNameDesc();
            case "phoneNumber":
                if(order) return empRepo.findAllByOrderByPhoneNumberAsc(); else return empRepo.findAllByOrderByPhoneNumberDesc();
            case "email":
                if(order) return empRepo.findAllByOrderByEmailAsc(); else return empRepo.findAllByOrderByEmailDesc();
            case "salary":
                if(order) return empRepo.findAllByOrderBySalaryAsc(); else return empRepo.findAllByOrderBySalaryDesc();
            case "startShift":
                if(order) return empRepo.findAllByOrderByStartShiftTimeAsc(); else return empRepo.findAllByOrderByStartShiftTimeDesc();
            case "endShift":
                if(order) return empRepo.findAllByOrderByEndShiftTimeAsc(); else return empRepo.findAllByOrderByEndShiftTimeDesc();
            default:
                return null;
        }
    }
}
