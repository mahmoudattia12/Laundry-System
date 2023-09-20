package com.example.LaundrySystem.Controller.ServerController;

import com.example.LaundrySystem.Controller.ServiceProvider.ItemServices;
import com.example.LaundrySystem.Controller.ServiceProvider.NotesServices;
import com.example.LaundrySystem.Controller.ServiceProvider.OrderServices;
import com.example.LaundrySystem.Entities.Order;
import com.example.LaundrySystem.Entities.OrderItem;
import com.example.LaundrySystem.Entities.OrderNote;
import com.example.LaundrySystem.Entities.ReceivedOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@CrossOrigin(origins = "http://127.0.0.1:5173/")
public class OrderController {
    @Autowired
    OrderServices orderServices;
    @Autowired
    ItemServices itemServices;
    @Autowired
    NotesServices notesServices;

    @PostMapping("/add")
    public String add(@RequestBody ReceivedOrder receivedOrder){
        return orderServices.add(receivedOrder);
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

    @PostMapping("/addNotes")
    public String addNotes(@RequestBody String[] notes ,@RequestParam("orderID") int orderID){
        try {
//            int ID = Integer.parseInt(orderID);
            for(String n : notes){
                String response = notesServices.add(orderID, n);
                if(!response.equals("SUCCESS")){
                    return response;
                }
            }
            return "SUCCESS";
        }catch (Exception e){
            return e.getMessage();
        }

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

    @PostMapping("/addItems")
    public String addItems(@RequestBody OrderItem[] orderItems, @RequestParam("orderID") int orderID){
        return itemServices.addItems(orderItems, orderID);
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