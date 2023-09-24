package com.example.LaundrySystem.Controller.ServerController;

import com.example.LaundrySystem.Controller.Filter.FilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5173/")
public class FilterController <T extends Comparable<T>> {
    @Autowired
    FilterService filterService;

    @GetMapping("/filterEntity/{entity}/{criteria}/{toMeet}/{laundryName}")
    public List<T> filterEntity(@PathVariable String entity, @PathVariable String criteria, @PathVariable String toMeet, @PathVariable String laundryName){
        return filterService.filter(entity, criteria, toMeet, laundryName);
    }
}
