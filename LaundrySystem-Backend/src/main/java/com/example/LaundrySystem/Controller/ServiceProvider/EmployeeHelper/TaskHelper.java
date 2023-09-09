package com.example.LaundrySystem.Controller.ServiceProvider.EmployeeHelper;

import com.example.LaundrySystem.Entities.Employee;
import com.example.LaundrySystem.Entities.EmployeeTask;
import com.example.LaundrySystem.Entities.TaskPrimaryKey;
import com.example.LaundrySystem.Repositories.EmployeeTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TaskHelper implements IHelper{
    @Autowired
    private EmployeeTaskRepository empTaskRepo;
    @Override
    public String add(Employee addFor, String toAdd) {
        TaskPrimaryKey taskPK = new TaskPrimaryKey(addFor, toAdd);
        Optional<EmployeeTask> checkTask = empTaskRepo.findById(taskPK);
        if(checkTask.isPresent()){
            return "Already Exists";
        }else{
            EmployeeTask empTask = new EmployeeTask(addFor, toAdd);
            empTaskRepo.save(empTask);
            return "SUCCESS";
        }
    }

    @Override
    public String update(Employee updateFor, String prev, String updateWith) {
        TaskPrimaryKey taskPK = new TaskPrimaryKey(updateFor, prev);
        Optional<EmployeeTask> checkTask = empTaskRepo.findById(taskPK);
        if(checkTask.isPresent()){
            empTaskRepo.delete(checkTask.get());
            EmployeeTask empTask = new EmployeeTask(updateFor, updateWith);
            empTaskRepo.save(empTask);
            return "SUCCESS";
        }else{
            return "OldTask Not Found";
        }
    }

    @Override
    public String delete(Employee deleteFor, String toDelete) {
        TaskPrimaryKey taskPK = new TaskPrimaryKey(deleteFor, toDelete);
        Optional<EmployeeTask> checkTask = empTaskRepo.findById(taskPK);
        if(checkTask.isPresent()){
            empTaskRepo.delete(checkTask.get());
            return "SUCCESS";
        }else{
            return "Not Found";
        }
    }
}
