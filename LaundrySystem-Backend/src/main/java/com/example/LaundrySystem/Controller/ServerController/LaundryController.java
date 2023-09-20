package com.example.LaundrySystem.Controller.ServerController;

import com.example.LaundrySystem.Controller.ServiceProvider.LaundryServices;
import com.example.LaundrySystem.Entities.Employee;
import com.example.LaundrySystem.Entities.Laundry;
import com.example.LaundrySystem.Repositories.LaundryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/laundry")
@CrossOrigin(origins = "http://127.0.0.1:5173/")
public class LaundryController <T extends Comparable<T>> {
    @Autowired
    LaundryServices laundryServices;

    @PostMapping("/signup")
    public String signup(@RequestBody Laundry laundry){

        return laundryServices.signup(laundry);
    }

    @PostMapping("/login")
    public String login(@RequestBody Laundry laundry){
        return laundryServices.login(laundry);
    }

    @PutMapping("/update/{updater}/{laundryName}/{newAddress}")
    public String update(@PathVariable String updater, @PathVariable String laundryName, @PathVariable String newAddress){
        return laundryServices.update(updater, laundryName, newAddress);
    }

    @GetMapping("/getEntityTable/{laundryName}/{entity}")
    public List<T> getEmployees(@PathVariable String laundryName, @PathVariable String entity){
        return laundryServices.getEntityInstances(laundryName, entity);
    }


}