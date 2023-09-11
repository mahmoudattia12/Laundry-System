package com.example.LaundrySystem.Controller.ServerController;

import com.example.LaundrySystem.Controller.ServiceProvider.EmployeeServices;
import com.example.LaundrySystem.Entities.Employee;
import com.example.LaundrySystem.Entities.EmployeeHoliday;
import com.example.LaundrySystem.Entities.EmployeeTask;
import com.example.LaundrySystem.Repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/emp")
public class EmployeeController {
    @Autowired
    private EmployeeServices employeeServices;

    @PostMapping("/managerSignup")
    public String managerSignup(@RequestBody String[] emp){
        System.out.println("hi from managerSignup");
        return employeeServices.signup(emp, true);
    }

    @PostMapping("/signup")
    public String signup(@RequestBody String[] emp) {
        System.out.println("hi from signup");
        return employeeServices.signup(emp, false);
    }

    @PostMapping("/login")
    public String login(@RequestParam("userName") String userName, @RequestParam("password") String password){
        System.out.println("hi from login");
        return employeeServices.login(userName, password);
    }

    @PutMapping("/update/{updaterID}/{toUpdateID}")
    public String update(@PathVariable String updaterID, @PathVariable String toUpdateID, @RequestBody Employee employee){
        System.out.println("hi from update");
        System.out.println(updaterID);
        System.out.println( toUpdateID);
        return employeeServices.update(updaterID, toUpdateID, employee);
    }

    @DeleteMapping("/delete/{deleterID}/{toDeleteID}")
    public String delete(@PathVariable String deleterID, @PathVariable String toDeleteID){
        return employeeServices.delete(deleterID, toDeleteID);
    }
    @GetMapping("/getAll")
    public List<Employee> getAll(){
        return employeeServices.getAllEmployees();
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
