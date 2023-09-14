package com.example.LaundrySystem.Controller.ServiceProvider.EmployeeHelper;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class HelperFactory {
    @Autowired
    private HolidayHelper holidayHelper;
    @Autowired
    private TaskHelper taskHelper;
    public IHelper getHelper(boolean helperType){
        if(helperType) return taskHelper;
        else return holidayHelper;
    }
}
