package com.example.LaundrySystem.Controller.ServerController;

import com.example.LaundrySystem.Controller.Filter.FilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FilterController <T extends Comparable<T>> {
    @Autowired
    FilterService filterService;

    @GetMapping("/filterEntity/{entity}/{criteria}/{toMeet}")
    public List<T> filterEntity(@PathVariable String entity, @PathVariable String criteria, @PathVariable String toMeet){
        return filterService.filter(entity, criteria, toMeet);
    }
}
