//package com.example.LaundrySystem.Controller.ServiceProvider;
//
//import com.example.LaundrySystem.Entities.Employee;
//import com.example.LaundrySystem.Repositories.EmployeeRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Example;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.data.repository.query.FluentQuery;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.function.Function;
//
//@Service
//public class EmployeeServices {
//    private static EmployeeServices instance = null;
//    @Autowired
//    EmployeeRepository empRepo;
//
//    public static EmployeeServices getInstance(){
//        if(instance == null){
//            instance = new EmployeeServices();
//        }
//        return instance;
//    }
//
//    public String addEmployee(Employee emp){
////        Employee emp = new Employee(EmployeeData[0], EmployeeData[1], EmployeeData[2], EmployeeData[3]);
//        try {
//            this.empRepo.save(emp);
//            return "SUCCESS";
//        }catch (Exception e){
//            System.out.println(e.getMessage());
//            return e.getMessage();
//        }
//
//    }
//}

package com.example.LaundrySystem.Controller.ServiceProvider;

import com.example.LaundrySystem.Controller.ServiceProvider.EmployeeHelper.HelperFactory;
import com.example.LaundrySystem.Entities.Employee;
import com.example.LaundrySystem.Entities.EmployeeHoliday;
import com.example.LaundrySystem.Entities.EmployeeTask;
import com.example.LaundrySystem.Repositories.EmployeeHolidayRepository;
import com.example.LaundrySystem.Repositories.EmployeeRepository;
import com.example.LaundrySystem.Repositories.EmployeeTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServices {
    @Autowired
    private EmployeeRepository empRepo;
    @Autowired
    private EmployeeTaskRepository empTaskRepo;
    @Autowired
    private EmployeeHolidayRepository empHolidayRepo;
    @Autowired
    private HelperFactory helperFactory;
    public String signup(String[] emp, boolean isManager) {
        try {
            Optional<Employee> checkEmp = empRepo.findById(emp[0]);
            if(checkEmp.isPresent()){
                return "ALREADY EXISTS";
            }else{
                Employee employee = new Employee(emp[0], emp[1], emp[2], emp[3], isManager);
                empRepo.save(employee);
                return "SUCCESS";
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
            return e.getMessage();
        }
    }

    public String login(String userName, String password){
        try {
            Optional<Employee> emp = empRepo.findById(userName);
            if(emp.isPresent()){
                if(emp.get().getPassword().equals(password)){
                    return "SUCCESS";
                }else{
                    return "INCORRECT";
                }
            }else{
                return "NOT FOUND";
            }
        } catch (Exception e){
            return e.getMessage();
        }
    }

    public String update(String updaterID, String toUpdateID, Employee newEmployee){
        try {
            String permission = isAllowed(updaterID);
            if(permission.equals("A")){
                Optional<Employee> toUpdate = empRepo.findById(toUpdateID);
                if(toUpdate.isPresent()){
                    if(newEmployee.getUserName().equalsIgnoreCase(toUpdateID)){
                        empRepo.save(newEmployee);
                        return "SUCCESS";
                    }else{
                        return "Fail";
                    }
                }else{
                    return "ToUpdate Not Found";
                }
            }else return permission;
        }catch (Exception e){
            return e.getMessage();
        }
    }
    public String delete(String deleterID, String toDeleteID){
        try {
            String permission = isAllowed(deleterID);
            if(permission.equals("A")){
                Optional<Employee> toDelete = empRepo.findById(toDeleteID);
                if(toDelete.isPresent()){
                    empRepo.deleteById(toDeleteID);
                    return "SUCCESS";
                }else{
                    return "ToDelete Not Found";
                }
            }else return permission;
        }catch (Exception e){
            return e.getMessage();
        }
    }
    public List<Employee> getAllEmployees(){
        try {
            return empRepo.findAll();
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    public Employee getByID(String ID){
        try {
            Optional<Employee> emp = empRepo.findById(ID);
            if(emp.isPresent()) return emp.get();
            else return null;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public String addByHelper(String adderID, String addForID, String toAdd, boolean helperType){
        try {
            String permission = isAllowed(adderID);
            if(permission.equals("A")){
                Optional<Employee> addFor = empRepo.findById(addForID);
                if(addFor.isPresent()){
                    Employee emp = addFor.get();

                    return helperFactory.getHelper(helperType).add(emp, toAdd);
                }else{
                    return "AddFor Not Found";
                }
            }else return permission;
        }catch (Exception e){
            return e.getMessage();
        }
    }

    public String updateByHelper(String updaterID, String updateForID, String old, String updateWith, boolean helperType){
        try {
            String permission = isAllowed(updaterID);
            if(permission.equals("A")){
                Optional<Employee> updateFor = empRepo.findById(updateForID);
                if(updateFor.isPresent()){
                    Employee emp = updateFor.get();
                    return helperFactory.getHelper(helperType).update(emp, old, updateWith);
                }else{
                    return "UpdateFor Not Found";
                }
            }else return permission;
        }catch (Exception e){
            return e.getMessage();
        }
    }

    public String deleteByHelper(String deleterID, String deleteForID, String toDelete, boolean helperType){
        try {
            String permission = isAllowed(deleterID);
            if(permission.equals("A")){
                Optional<Employee> deleteFor = empRepo.findById(deleteForID);
                if(deleteFor.isPresent()){
                    Employee emp = deleteFor.get();
                    return helperFactory.getHelper(helperType).delete(emp, toDelete);
                }else{
                    return "UpdateFor Not Found";
                }
            }else return permission;
        }catch (Exception e){
            return e.getMessage();
        }
    }
    public List<EmployeeTask> getAllTasks(){
        try {
            return empTaskRepo.findAll();
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    public List<String> getTasksByID(String empID){
        try {
            Optional<Employee> emp = empRepo.findById(empID);
            if(emp.isPresent()){
                return emp.get().getTasksMessages();
            }
            return null;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    public List<EmployeeHoliday> getAllHolidays(){
        try {
            return empHolidayRepo.findAll();
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    public List<String> getHolidaysByID(String empID){
        try {
            Optional<Employee> emp = empRepo.findById(empID);
            if(emp.isPresent()){
                return emp.get().getHolidayMessages();
            }
            return null;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    private String isAllowed(String empID){
        try {
            Optional<Employee> emp = empRepo.findById(empID);
            if(emp.isPresent()){
                if(emp.get().isManager()) return "A";
                else return "Not Allowed";
            }else return "Manager Not Found";
        }catch (Exception e) {
            return e.getMessage();
        }
    }
}
