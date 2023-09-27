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
import com.example.LaundrySystem.Entities.*;
import com.example.LaundrySystem.Repositories.EmployeeHolidayRepository;
import com.example.LaundrySystem.Repositories.EmployeeRepository;
import com.example.LaundrySystem.Repositories.EmployeeTaskRepository;
import com.example.LaundrySystem.Repositories.LaundryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
//@Transactional
public class EmployeeServices {
    @Autowired
    private EmployeeRepository empRepo;
    @Autowired
    private EmployeeTaskRepository empTaskRepo;
    @Autowired
    private EmployeeHolidayRepository empHolidayRepo;
    @Autowired
    private LaundryRepository laundryRepo;
    @Autowired
    private HelperFactory helperFactory;

    public String signup(Employee emp, String laundryName) {
        try {
            Optional<Employee> checkEmp = empRepo.findById(emp.getUserName());
            if (checkEmp.isPresent()) {
                return "ALREADY EXISTS";
            } else {
                if (emp.getUserName().equalsIgnoreCase("SystemAdmin")
                        && emp.getPassword().equals("1234$$$system&&&admin&&&system$$$6789")) {
                    emp.setManager(true);
                }

                Optional<Laundry> checkLaundry = laundryRepo.findById(laundryName);
                if (checkLaundry.isPresent()) {
                    emp.setLaundry(checkLaundry.get());
                    empRepo.save(emp);
                    return "SUCCESS";
                } else {
                    return "Laundry Not Found";
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return e.getMessage();
        }
    }

    public String login(Employee employee, String laundryName) {
        try {
            Optional<Employee> emp = empRepo.findById(employee.getUserName());
            if (emp.isPresent()) {
                Optional<Laundry> checkLaundry = laundryRepo.findById(laundryName);
                if (checkLaundry.isPresent()) {
                    if (emp.get().getLaundry().getName().equals(laundryName)) {
                        if (emp.get().getPassword().equals(employee.getPassword())) {
                            return "SUCCESS";
                        } else {
                            return "INCORRECT PASSWORD";
                        }
                    } else {
                        return "Employee Not Signed Up to This Laundry";
                    }
                } else {
                    return "Laundry Not Found";
                }
            } else {
                return "Employee Not Found";
            }
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String update(String updaterID, String toUpdateID, String laundryName, ReceivedEmployee newEmployee) {
        try {
            String permission = isAllowed(updaterID);
            if (permission.equals("A")) {
                Optional<Employee> toUpdate = empRepo.findById(toUpdateID);
                if (toUpdate.isPresent()) {
                    Optional<Laundry> checkLaundry = laundryRepo.findById(laundryName);
                    if (checkLaundry.isPresent()) {

                        if (toUpdate.get().getLaundry().getName().equals(laundryName) && newEmployee.getUserName().equalsIgnoreCase(toUpdateID)) {
                            Employee employee = toUpdate.get();
                            if (toUpdateID.equals("SystemAdmin")) {
                                if (!newEmployee.getIsManager()) {
                                    return "Can't make the root manager not manager";
                                }
                            }
                            employee.setManager(newEmployee.getIsManager());
                            employee.setPhoneNumber(newEmployee.getPhoneNumber());
                            employee.setEmail(newEmployee.getEmail());
                            employee.setSalary(newEmployee.getSalary());
                            employee.setStartShiftTime(newEmployee.getStartShiftTime());
                            employee.setEndShiftTime(newEmployee.getEndShiftTime());

                            empHolidayRepo.deleteByEmployee(employee);
                            empTaskRepo.deleteByEmployee(employee);

                            List<EmployeeHoliday> empHolidays = new ArrayList<>();
                            for (String holiday : newEmployee.getHolidays()) {
                                HolidayPrimaryKey holidayPK = new HolidayPrimaryKey(employee, holiday);
                                Optional<EmployeeHoliday> checkHoliday = empHolidayRepo.findById(holidayPK);
                                if (!checkHoliday.isPresent()) {
                                    empHolidays.add(new EmployeeHoliday(employee, holiday));
                                }
                            }
                            employee.setHolidays(empHolidays);

                            List<EmployeeTask> tasks = new ArrayList<>();
                            for (String task : newEmployee.getTasks()) {
                                EmployeeTask employeeTask = new EmployeeTask(employee, task);
                                if (!tasks.contains(employeeTask)) {
                                    tasks.add(employeeTask);
                                }
                            }
                            employee.setTasks(tasks);


                            empRepo.save(employee);
                            return "SUCCESS";
                        } else {
                            return "Fail";
                        }
                    } else {
                        return "Laundry Not Found";
                    }

                } else {
                    return "ToUpdate Not Found";
                }
            } else return permission;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String delete(String deleterID, String toDeleteID, String laundryName) {
        try {
            String permission = isAllowed(deleterID);
            if (permission.equals("A")) {
                Optional<Employee> toDelete = empRepo.findById(toDeleteID);
                if (toDelete.isPresent()) {
                    if(!toDeleteID.equals(deleterID)){
                        if (!toDeleteID.equals("SystemAdmin")) {
                            Optional<Laundry> checkLaundry = laundryRepo.findById(laundryName);
                            if (checkLaundry.isPresent()) {
                                if (toDelete.get().getLaundry().getName().equals(laundryName)) {
                                    empRepo.deleteById(toDeleteID);
                                    return "SUCCESS";

                                } else return "FAIL";

                            } else return "Laundry Not Found";

                        } else return "Not Allowed To Delete The Root Manager";

                    }else{
                        return "Can't delete yourself";
                    }
                } else {
                    return "ToDelete Not Found";
                }
            } else return permission;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public List<Employee> getAllEmployees(String laundryName, String currEmpName, String page) {

        try {
            Optional<Employee> checkEmp = empRepo.findById(currEmpName);
            if (checkEmp.isPresent()) {
                if (checkEmp.get().isManager() || page.equals("empInfo")) {
                    Optional<Laundry> checkLaundry = laundryRepo.findById(laundryName);
                    if (checkLaundry.isPresent()) {

                        return checkLaundry.get().getEmployees();
                    } else {
                        return null;
                    }
                } else {
                    List<Employee> list = new ArrayList<>();
                    return list;
                }
            } else {
                return null;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Employee getByID(String ID) {
        try {
            Optional<Employee> emp = empRepo.findById(ID);
            if (emp.isPresent()) return emp.get();
            else return null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public String addByHelper(String adderID, String addForID, String toAdd, boolean helperType) {
        try {
            String permission = isAllowed(adderID);
            if (permission.equals("A")) {
                Optional<Employee> addFor = empRepo.findById(addForID);
                if (addFor.isPresent()) {
                    Employee emp = addFor.get();

                    return helperFactory.getHelper(helperType).add(emp, toAdd);
                } else {
                    return "AddFor Not Found";
                }
            } else return permission;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String updateByHelper(String updaterID, String updateForID, String old, String updateWith, boolean helperType) {
        try {
            String permission = isAllowed(updaterID);
            if (permission.equals("A")) {
                Optional<Employee> updateFor = empRepo.findById(updateForID);
                if (updateFor.isPresent()) {
                    Employee emp = updateFor.get();
                    return helperFactory.getHelper(helperType).update(emp, old, updateWith);
                } else {
                    return "UpdateFor Not Found";
                }
            } else return permission;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String deleteByHelper(String deleterID, String deleteForID, String toDelete, boolean helperType) {
        try {
            String permission = isAllowed(deleterID);
            if (permission.equals("A")) {
                Optional<Employee> deleteFor = empRepo.findById(deleteForID);
                if (deleteFor.isPresent()) {
                    Employee emp = deleteFor.get();
                    return helperFactory.getHelper(helperType).delete(emp, toDelete);
                } else {
                    return "UpdateFor Not Found";
                }
            } else return permission;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public List<EmployeeTask> getAllTasks() {
        try {
            return empTaskRepo.findAll();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public List<String> getTasksByID(String empID) {
        try {
            Optional<Employee> emp = empRepo.findById(empID);
            if (emp.isPresent()) {
                return emp.get().getTasksMessages();
            }
            return null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public List<EmployeeHoliday> getAllHolidays() {
        try {
            return empHolidayRepo.findAll();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public List<String> getHolidaysByID(String empID) {
        try {
            Optional<Employee> emp = empRepo.findById(empID);
            if (emp.isPresent()) {
                return emp.get().getHolidayMessages();
            }
            return null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private String isAllowed(String empID) {
        try {
            Optional<Employee> emp = empRepo.findById(empID);
            if (emp.isPresent()) {
                if (emp.get().isManager()) return "A";
                else return "Not Allowed";
            } else return "Manager Not Found";
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
