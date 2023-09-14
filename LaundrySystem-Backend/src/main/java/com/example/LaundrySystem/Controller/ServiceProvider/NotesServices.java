package com.example.LaundrySystem.Controller.ServiceProvider;

import com.example.LaundrySystem.Entities.NotePrimaryKey;
import com.example.LaundrySystem.Entities.Order;
import com.example.LaundrySystem.Entities.OrderNote;
import com.example.LaundrySystem.Repositories.OrderNoteRepository;
import com.example.LaundrySystem.Repositories.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class NotesServices {
    @Autowired
    OrderNoteRepository noteRepo;
    @Autowired
    OrderRepository orderRepo;

    public String add(int orderID, String note){
        try {
            Optional<Order> check = orderRepo.findById(orderID);
            if(check.isPresent()){
                Order order = check.get();
                NotePrimaryKey notePK = new NotePrimaryKey(order, note);
                Optional<OrderNote> checkNote = noteRepo.findById(notePK);
                if(checkNote.isPresent()){
                    return "Already Exist";
                }else{
                    OrderNote orderNote = new OrderNote(order, note);
                    noteRepo.save(orderNote);
                    return "SUCCESS";
                }
            }else{
                return "Order Not Found";
            }
        }catch (Exception e){
            return e.getMessage();
        }
    }

    public String update(int orderID, String oldNote, String newNote){
        try {
            Optional<Order> check = orderRepo.findById(orderID);
            if(check.isPresent()){
                Order order = check.get();
                NotePrimaryKey notePK = new NotePrimaryKey(order, oldNote);
                Optional<OrderNote> checkNote = noteRepo.findById(notePK);
                if(checkNote.isPresent()){
                    noteRepo.delete(checkNote.get());
                    OrderNote orderNote = new OrderNote(order, newNote);
                    noteRepo.save(orderNote);
                    return "SUCCESS";
                }else{
                    return "Note Not Found";
                }
            }else{
                return "Order Not Found";
            }
        }catch (Exception e){
            return e.getMessage();
        }
    }

    public String delete(int orderID, String note){
        try {
            Optional<Order> check = orderRepo.findById(orderID);
            if(check.isPresent()){
                Order order = check.get();
                NotePrimaryKey notePK = new NotePrimaryKey(order, note);
                Optional<OrderNote> checkNote = noteRepo.findById(notePK);
                if(checkNote.isPresent()){
                    noteRepo.delete(checkNote.get());
                    return "SUCCESS";
                }else{
                    return "Note Not Found";
                }
            }else{
                return "Order Not Found";
            }
        }catch (Exception e){
            return e.getMessage();
        }
    }

    public List<OrderNote> getAll(){
        try {
            return noteRepo.findAll();
        }catch (Exception e){
            return null;
        }
    }

    public List<String> getById(int orderID){
        try {
            Optional<Order> checkOrder = orderRepo.findById(orderID);
            if(checkOrder.isPresent()){
                return checkOrder.get().getNotesMessages();
            }else{
                return null;
            }
        }catch (Exception e){
            return null;
        }
    }
}
