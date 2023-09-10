package com.example.LaundrySystem.Controller.ServiceProvider.EmployeeHelper;

import com.example.LaundrySystem.Entities.*;
import com.example.LaundrySystem.Repositories.EmployeeHolidayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HolidayHelper implements IHelper {
    @Autowired
    EmployeeHolidayRepository empHolidayRepo;

    public String add(Employee addFor, String toAdd) {
        HolidayPrimaryKey holidayPK = new HolidayPrimaryKey(addFor, toAdd);
        Optional<EmployeeHoliday> checkHoliday = empHolidayRepo.findById(holidayPK);
        if(checkHoliday.isPresent()){
            return "Already Exists";
        }else{
            EmployeeHoliday empHoliday = new EmployeeHoliday(addFor, toAdd);
            empHolidayRepo.save(empHoliday);
            return "SUCCESS";
        }
    }

    public String update(Employee updateFor, String prev, String updateWith) {
        HolidayPrimaryKey holidayPK = new HolidayPrimaryKey(updateFor, prev);
        Optional<EmployeeHoliday> checkHoliday = empHolidayRepo.findById(holidayPK);
        if(checkHoliday.isPresent()){
            empHolidayRepo.delete(checkHoliday.get());
            EmployeeHoliday empHoliday = new EmployeeHoliday(updateFor, updateWith);
            empHolidayRepo.save(empHoliday);
            return "SUCCESS";
        }else{
            return "Old Holiday Not Found";
        }
    }

    public String delete(Employee deleteFor, String toDelete) {
        HolidayPrimaryKey holidayPK = new HolidayPrimaryKey(deleteFor, toDelete);
        Optional<EmployeeHoliday> checkHoliday = empHolidayRepo.findById(holidayPK);
        if(checkHoliday.isPresent()){
            empHolidayRepo.delete(checkHoliday.get());
            return "SUCCESS";
        }else{
            return "Not Found";
        }
    }
}
