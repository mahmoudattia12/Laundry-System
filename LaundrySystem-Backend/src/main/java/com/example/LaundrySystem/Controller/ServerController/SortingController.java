package com.example.LaundrySystem.Controller.ServerController;

import com.example.LaundrySystem.Controller.Sorting.SortServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SortingController <T extends Comparable<T>> {
    @Autowired
    SortServices sortServices;
    @GetMapping("/sortEntity/{entity}/{sortBy}/{order}")
    public List<T> sortEntity(@PathVariable String entity, @PathVariable String sortBy, @PathVariable boolean order){
        return sortServices.sort(entity, sortBy, order);
    }
}
