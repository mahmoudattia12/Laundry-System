package com.example.LaundrySystem.Controller.ServerController;

import com.example.LaundrySystem.Controller.ServiceProvider.LaundryServices;
import com.example.LaundrySystem.Entities.Laundry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/laundry")
public class LaundryController {
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
}