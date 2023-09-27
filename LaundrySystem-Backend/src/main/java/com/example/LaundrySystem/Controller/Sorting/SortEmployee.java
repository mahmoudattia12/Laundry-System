package com.example.LaundrySystem.Controller.Sorting;

import com.example.LaundrySystem.Entities.Employee;
import com.example.LaundrySystem.Repositories.EmployeeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class SortEmployee <T extends Comparable<T>> implements ISorterStrategy {
    @Autowired
    EmployeeRepository empRepo;

    @Override
    public List<Employee> sort(String sortBy, boolean order, String laundryName) {
        try{
            switch (sortBy){
                case "userName":
                    if(order) return empRepo.findAllByLaundryNameOrderByUserNameAsc(laundryName); else return empRepo.findAllByLaundryNameOrderByUserNameDesc(laundryName);
                case "phoneNumber":
                    if(order) return empRepo.findAllByLaundryNameOrderByPhoneNumberAsc(laundryName); else return empRepo.findAllByLaundryNameOrderByPhoneNumberDesc(laundryName);
                case "email":
                    if(order) return empRepo.findAllByLaundryNameOrderByEmailAsc(laundryName); else return empRepo.findAllByLaundryNameOrderByEmailDesc(laundryName);
                case "salary":
                    if(order) return empRepo.findAllByLaundryNameOrderBySalaryAsc(laundryName); else return empRepo.findAllByLaundryNameOrderBySalaryDesc(laundryName);
                case "startShift":
                    if(order) return empRepo.findAllByLaundryNameOrderByStartShiftTimeAsc(laundryName); else return empRepo.findAllByLaundryNameOrderByStartShiftTimeDesc(laundryName);
                case "endShift":
                    if(order) return empRepo.findAllByLaundryNameOrderByEndShiftTimeAsc(laundryName); else return empRepo.findAllByLaundryNameOrderByEndShiftTimeDesc(laundryName);
                default:
                    return null;
            }
        }catch (Exception e){
            return null;
        }
    }
}
