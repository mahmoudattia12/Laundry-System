package com.example.LaundrySystem.Controller.ServerController;

import com.example.LaundrySystem.Controller.ServiceProvider.EmployeeServices;
import com.example.LaundrySystem.Entities.*;
import com.example.LaundrySystem.Repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/emp")
@CrossOrigin(origins = "http://127.0.0.1:5173/")
public class EmployeeController {
    @Autowired
    private EmployeeServices employeeServices;

    @PostMapping("/signup")
    public String signup(@RequestBody Employee emp, @RequestParam("laundryName") String laundryName) {
        System.out.println("hi from signup");
        return employeeServices.signup(emp, laundryName);
    }

    @PostMapping("/login")
    public String login(@RequestBody Employee emp, @RequestParam("laundryName") String laundryName){
        System.out.println("hi from login");
        return employeeServices.login(emp, laundryName);
    }

    @PutMapping("/update/{updaterID}/{toUpdateID}/{laundryName}")
    public String update(@PathVariable String updaterID, @PathVariable String toUpdateID, @PathVariable String laundryName, @RequestBody ReceivedEmployee employee){
        System.out.println("hi from update");
        System.out.println(updaterID);
        System.out.println( toUpdateID);
        return employeeServices.update(updaterID, toUpdateID, laundryName, employee);
    }

    @DeleteMapping("/delete/{deleterID}/{toDeleteID}/{laundryName}")
    public String delete(@PathVariable String deleterID, @PathVariable String toDeleteID, @PathVariable String laundryName){
        return employeeServices.delete(deleterID, toDeleteID, laundryName);
    }
    @GetMapping("/getAll/{laundryName}/{empUserName}/{page}")
    public List<Employee> getAll(@PathVariable String laundryName, @PathVariable String empUserName, @PathVariable String page){
        return employeeServices.getAllEmployees(laundryName, empUserName, page);
    }

    @GetMapping("/getByID/{empID}")
    public Employee getByID(@PathVariable String empID){
        return employeeServices.getByID(empID);
    }

    @PostMapping("/addTask")
    public String addTask(@RequestParam("adderID") String adderID, @RequestParam("addForID") String addForID, @RequestParam("task") String task){
        System.out.println(adderID);
        System.out.println(addForID);
        System.out.println(task);
        return employeeServices.addByHelper(adderID, addForID, task, true);
    }

    @PutMapping("/updateTask/{updaterID}/{updateForID}/{oldTask}/{newTask}")
    public String updateTask(@PathVariable String updaterID, @PathVariable String updateForID, @PathVariable String oldTask, @PathVariable String newTask){
        System.out.println("hi from update Task");
        System.out.println(updaterID);
        return employeeServices.updateByHelper(updaterID, updateForID, oldTask, newTask, true);
    }

    @DeleteMapping("/deleteTask/{deleterID}/{deleteForID}/{task}")
    public String deleteTask(@PathVariable String deleterID, @PathVariable String deleteForID, @PathVariable String task){
        return employeeServices.deleteByHelper(deleterID, deleteForID, task, true);
    }

    @GetMapping("/getAllTasks")
    public List<EmployeeTask> getAllTasks(){
        return employeeServices.getAllTasks();
    }

    @GetMapping("/getTasksByID/{empID}")
    public List<String> getTasksByID(@PathVariable String empID){
        return employeeServices.getTasksByID(empID);
    }

    @PostMapping("/addHoliday")
    public String addHoliday(@RequestParam("adderID") String adderID, @RequestParam("addForID") String addForID, @RequestParam("holiday") String holiday){
        return employeeServices.addByHelper(adderID, addForID, holiday, false);
    }

    @PutMapping("/updateHoliday/{updaterID}/{updateForID}/{oldHoliday}/{newHoliday}")
    public String updateHoliday(@PathVariable String updaterID, @PathVariable String updateForID, @PathVariable String oldHoliday, @PathVariable String newHoliday){
        return employeeServices.updateByHelper(updaterID, updateForID, oldHoliday, newHoliday, false);
    }

    @DeleteMapping("/deleteHoliday/{deleterID}/{deleteForID}/{Holiday}")
    public String deleteHoliday(@PathVariable String deleterID, @PathVariable String deleteForID, @PathVariable String Holiday){
        return employeeServices.deleteByHelper(deleterID, deleteForID, Holiday, false);
    }

    @GetMapping("/getAllHolidays")
    public List<EmployeeHoliday> getAllHolidays(){
        return employeeServices.getAllHolidays();
    }

    @GetMapping("/getHolidaysByID/{empID}")
    public List<String> getHolidaysByID(@PathVariable String empID){
        return employeeServices.getHolidaysByID(empID);
    }
}
