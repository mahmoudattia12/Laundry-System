package com.example.LaundrySystem.Controller.ServerController;

import com.example.LaundrySystem.Controller.Sorting.SortServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5173/")
public class SortingController <T extends Comparable<T>> {
    @Autowired
    SortServices sortServices;
    @GetMapping("/sortEntity/{entity}/{sortBy}/{order}/{laundryName}")
    public List<T> sortEntity(@PathVariable String entity, @PathVariable String sortBy, @PathVariable boolean order, @PathVariable String laundryName){
        return sortServices.sort(entity, sortBy, order, laundryName);
    }
}
