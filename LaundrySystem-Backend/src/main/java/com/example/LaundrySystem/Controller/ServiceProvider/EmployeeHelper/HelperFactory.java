package com.example.LaundrySystem.Controller.ServiceProvider.EmployeeHelper;

import org.springframework.stereotype.Service;

@Service
public class HelperFactory {
    public HelperFactory(){}
    public IHelper getHelper(boolean helperType){
        if(helperType) return new TaskHelper();
        else return new HolidayHelper();
    }
}
