package com.example.LaundrySystem.Controller.ServerController;

import com.example.LaundrySystem.Controller.ServiceProvider.ItemServices;
import com.example.LaundrySystem.Controller.ServiceProvider.NotesServices;
import com.example.LaundrySystem.Controller.ServiceProvider.OrderServices;
import com.example.LaundrySystem.Entities.Order;
import com.example.LaundrySystem.Entities.OrderItem;
import com.example.LaundrySystem.Entities.OrderNote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    OrderServices orderServices;
    @Autowired
    ItemServices itemServices;
    @Autowired
    NotesServices notesServices;

    @PostMapping("/add")
    public String add(@RequestBody Order order){
        return orderServices.add(order);
    }

    @PutMapping("/update/{orderID}")
    public String update(@PathVariable int orderID, @RequestBody Order newOrder){
        return orderServices.update(orderID, newOrder);
    }

    @DeleteMapping("/delete/{orderID}")
    public String delete(@PathVariable int orderID){
        return orderServices.delete(orderID);
    }

    @GetMapping("/getAll")
    public List<Order> getAll(){
        return orderServices.getAll();
    }

    @GetMapping("/getByID/{orderID}")
    public Order getByID(int orderID){
        return orderServices.getByID(orderID);
    }

    @PostMapping("/addNote")
    public String addNote(@RequestParam("orderID") int orderID, @RequestParam("note") String note){
        return notesServices.add(orderID, note);
    }

    @PutMapping("/updateNote/{orderID}/{oldNote}/{newNote}")
    public String updateNote(@PathVariable int orderID, @PathVariable String oldNote, @PathVariable String newNote){
        return notesServices.update(orderID, oldNote, newNote);
    }

    @DeleteMapping("/deleteNote/{orderID}/{note}")
    public String deleteNote(@PathVariable int orderID, @PathVariable String note){
        return notesServices.delete(orderID, note);
    }

    @GetMapping("/getAllNotes")
    public List<OrderNote> getAllNotes(){
        return notesServices.getAll();
    }

    @GetMapping("/getOrderNotes/{orderID}")
    public List<String> getOrderNotes(@PathVariable int orderID){
        return notesServices.getById(orderID);
    }

    @PostMapping("/addItem")
    public String addItem(@RequestBody OrderItem orderItem){
        return itemServices.addItem(orderItem);
    }

    @PutMapping("/updateItem/{orderID}/{type}/{service}")
    public String updateItem(@PathVariable int orderID, @PathVariable String type, @PathVariable String service, @RequestBody OrderItem orderItem){
        return itemServices.update(orderID, type, service, orderItem);
    }

    @DeleteMapping("/deleteItem/{orderID}/{type}/{service}")
    public String deleteItem(@PathVariable int orderID, @PathVariable String type, @PathVariable String service){
        return itemServices.delete(orderID, type, service);
    }

    @GetMapping("/getAllItems")
    public List<OrderItem> getAllItems(){
        return itemServices.getAll();
    }

    @GetMapping("/getOrderItems/{orderID}")
    public List<OrderItem> getOrderItems(@PathVariable int orderID){
        return itemServices.getByID(orderID);
    }
}