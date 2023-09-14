package com.example.LaundrySystem.Controller.ServiceProvider;

import com.example.LaundrySystem.Entities.Employee;
import com.example.LaundrySystem.Entities.Laundry;
import com.example.LaundrySystem.Repositories.EmployeeRepository;
import com.example.LaundrySystem.Repositories.LaundryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class LaundryServices {
    @Autowired
    LaundryRepository laundryRepo;
    @Autowired
    EmployeeRepository empRepo;

    public String signup(Laundry laundry){
        try {
            System.out.println(" hi from signup");
            System.out.println(laundry.getName());
            System.out.println(laundry.getPassword());
            System.out.println(laundry.getAddress());
            Optional<Laundry> laundryCheck = laundryRepo.findById(laundry.getName());
            if(laundryCheck.isPresent()){
                return "Already Exists";
            }else{
                laundryRepo.save(laundry);
                return "SUCCESS";
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            return e.getMessage();
        }
    }

    public String login(Laundry laundry){
        try {
            Optional<Laundry> laundryCheck = laundryRepo.findById(laundry.getName());
            if(laundryCheck.isPresent()){
                if(laundryCheck.get().getPassword().equals(laundry.getPassword())){
                    return "SUCCESS";
                }else{
                    return "Password In Correct";
                }
            }else{
                return "Laundry Not Found";
            }
        }catch (Exception e){
            return e.getMessage();
        }
    }

    public String update(String updater, String laundryName, String newAddress){
        try {
           Optional<Employee> checkEmp = empRepo.findById(updater);
           if(checkEmp.isPresent()){
               if(checkEmp.get().isManager()){
                   Optional<Laundry> checkLaundry = laundryRepo.findById(laundryName);
                   if(checkLaundry.isPresent()){
                       Laundry laundry = checkLaundry.get();
                       laundry.setAddress(newAddress);
                       laundryRepo.save(laundry);
                       return "SUCCESS";
                   }else{
                       return "Laundry Not Found";
                   }
               }else{
                   return "Not Allowed";
               }
           }else{
               return "Updater Not Found";
           }
        }catch (Exception e){
            return e.getMessage();
        }
    }
}
