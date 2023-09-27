import React, { useState, useEffect } from "react";
import { Employee } from "../pages/home/Employees";
import { Modal, Form, Button } from "react-bootstrap";
import axios from "axios";

interface HandleEmployeeProps {
  show: boolean;
  onHide: () => void;
  laundryName: string;
  currEmployee: string;
  oldEmployee: Employee | null;
  headerTitle: string;
  buttonName: string;
  onSuccess: (employee: Employee) => void;
}

const HandleEmployee = ({
  show,
  onHide,
  laundryName,
  currEmployee,
  oldEmployee,
  headerTitle,
  buttonName,
  onSuccess,
}: HandleEmployeeProps) => {
  const [formData, setFormData] = useState({
    userName: oldEmployee?.userName || "",
    phoneNumber: oldEmployee?.phoneNumber || "",
    email: oldEmployee?.email || "",
    startShiftTime: oldEmployee?.startShiftTime,
    endShiftTime: oldEmployee?.endShiftTime,
    salary: oldEmployee?.salary,
    isManager: oldEmployee?.isManager.toString() || "false",
  });
  const [employeeTasks, setEmployeeTasks] = useState<string[]>([]);
  const [employeeHolidays, setEmployeeHolidays] = useState<string[]>([]);
  const [formErrors, setFormErrors] = useState<
    Record<string, string | boolean>
  >({});

  useEffect(() => {
    if (oldEmployee) {
      console.log("hi from update useeffect and selected emp:", oldEmployee);
      setEmployeeTasks(oldEmployee.tasks || []);
      setEmployeeHolidays(oldEmployee.holidays || []);
      setFormData({
        userName: oldEmployee?.userName || "",
        phoneNumber: oldEmployee?.phoneNumber || "",
        email: oldEmployee?.email || "",
        startShiftTime: oldEmployee?.startShiftTime,
        endShiftTime: oldEmployee?.endShiftTime,
        salary: oldEmployee?.salary,
        isManager: oldEmployee?.isManager.toString() || "false",
      });
    }
  }, [oldEmployee]);

  const addNewTask = () => {
    setEmployeeTasks([...employeeTasks, ""]);
  };

  const deleteTask = (index: number) => {
    if (employeeTasks.length > 0) {
      const updatedTasks = [...employeeTasks];
      updatedTasks.splice(index, 1);
      setEmployeeTasks(updatedTasks);
    }
  };

  const addNewHoliday = () => {
    setEmployeeHolidays([...employeeHolidays, ""]);
  };

  const deleteHoliday = (index: number) => {
    if (employeeHolidays.length > 0) {
      const updatedHolidays = [...employeeHolidays];
      updatedHolidays.splice(index, 1);
      setEmployeeHolidays(updatedHolidays);
    }
  };

  const handleTasksChange = (index: number, value: string) => {
    const updatedTasks = [...employeeTasks];
    updatedTasks[index] = value;
    setEmployeeTasks(updatedTasks);
  };
  const handleHolidaysChange = (index: number, value: string) => {
    const updatedHolidays = [...employeeHolidays];
    updatedHolidays[index] = value;
    setEmployeeHolidays(updatedHolidays);
  };

  const handleFormChange = (field: keyof typeof formData, value: any) => {
    setFormData({
      ...formData,
      [field]: value,
    });
  };

  const validateForm = () => {
    const errors: Record<string, string | boolean> = {};

    employeeTasks.forEach((task, index) => {
      if (!task) {
        errors[`task${index}`] = "Task is required";
      }
    });

    employeeHolidays.forEach((holiday, index) => {
      if (!holiday) {
        errors[`holiday${index}`] = "Holiday is required";
      }
    });

    const phonePattern = /^(0\d{10}|0\d{8})$/;
    if (!phonePattern.test(formData.phoneNumber)) {
      errors.phoneNumber =
        "Phone must be exactly 9 or 11 digits and start with 0";
    }

    const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailPattern.test(formData.email)) {
      errors.email = "Invalid email format(---@---)";
    }

    if (formData.salary && formData.salary < 0) {
      errors.salary = "salary must be positive number";
    }
    setFormErrors(errors);
    return Object.keys(errors).length === 0;
  };

  const resetWindow = () => {
    setFormData({
      userName: oldEmployee?.userName || "",
      phoneNumber: oldEmployee?.phoneNumber || "",
      email: oldEmployee?.email || "",
      startShiftTime: oldEmployee?.startShiftTime,
      endShiftTime: oldEmployee?.endShiftTime,
      salary: oldEmployee?.salary,
      isManager: oldEmployee?.isManager.toString() || "false",
    });
    setEmployeeTasks([]);
    setEmployeeHolidays([]);
    setFormErrors({});
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (validateForm()) {
      console.log("update is validated");
      console.log(formData);
      console.log(employeeTasks);
      console.log(employeeHolidays);
      const toSent: any = {};
      toSent.userName = oldEmployee?.userName;
      toSent.email = formData.email.trim();
      toSent.phoneNumber = formData.phoneNumber;
      toSent.isManager = formData.isManager === "true" ? true : false;
      toSent.tasks = employeeTasks;
      toSent.holidays = employeeHolidays;
      toSent.salary = formData.salary;
      toSent.startShiftTime = formData.startShiftTime;
      toSent.endShiftTime = formData.endShiftTime;

      console.log("to send : ", toSent);

      if (toSent.userName) {
        try {
          const response = await axios.put(
            `http://localhost:9080/emp/update/${currEmployee}/${toSent.userName}/${laundryName}`,
            toSent
          );
          console.log("update response : -- ", response.data);

          if (response.data === "SUCCESS") {
            const setEmp: any = {};
            setEmp.userName = toSent.userName;
            setEmp.phoneNumber = toSent.phoneNumber;
            setEmp.email = toSent.email;
            setEmp.isManager = toSent.isManager;
            setEmp.tasks = toSent.tasks ? toSent.tasks : [];
            setEmp.holidays = toSent.holidays ? toSent.holidays : [];
            setEmp.salary = toSent.salary ? toSent.salary : null;
            setEmp.startShiftTime = toSent.startShiftTime
              ? toSent.startShiftTime
              : null;
            setEmp.endShiftTime = toSent.endShiftTime
              ? toSent.endShiftTime
              : null;
            onSuccess(setEmp);
            alert(response.data);
            resetWindow();
            onHide();
          } else {
            alert(response.data);
          }
        } catch (error) {
          console.error("Error:", error);
          alert(error);
        }
      }
    }
  };

  return (
    <Modal show={show} onHide={onHide} size="lg" dialogClassName="custom-modal">
      <Modal.Header closeButton onClick={resetWindow}>
        <Modal.Title>{headerTitle}</Modal.Title>
      </Modal.Header>

      <Modal.Body>
        <Form onSubmit={handleSubmit}>
          <Form.Group>
            <div className="row mb-3">
              <div className="col-md-6">
                <Form.Label>Phone</Form.Label>
                <Form.Control
                  type="tel"
                  placeholder="Phone"
                  value={formData.phoneNumber}
                  onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
                    handleFormChange("phoneNumber", e.target.value)
                  }
                  isInvalid={!!formErrors[`phoneNumber`]}
                />
                <Form.Control.Feedback type="invalid">
                  {formErrors[`phoneNumber`]}
                </Form.Control.Feedback>
              </div>

              <div className="col-md-6">
                <Form.Label>Email</Form.Label>
                <Form.Control
                  type="email"
                  placeholder="Email"
                  value={formData.email}
                  onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
                    handleFormChange("email", e.target.value)
                  }
                  isInvalid={!!formErrors[`email`]}
                />
                <Form.Control.Feedback type="invalid">
                  {formErrors[`email`]}
                </Form.Control.Feedback>
              </div>
            </div>
          </Form.Group>

          <Form.Group>
            <div className="row mb-3">
              <div className="col-md-6">
                <Form.Label>Start Shift Time</Form.Label>
                <Form.Control
                  type="time"
                  placeholder="Start Shift Time"
                  value={formData.startShiftTime}
                  onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
                    handleFormChange("startShiftTime", e.target.value)
                  }
                  isInvalid={!!formErrors[`startShiftTime`]}
                />
                <Form.Control.Feedback type="invalid">
                  {formErrors[`startShiftTime`]}
                </Form.Control.Feedback>
              </div>
              <div className="col-md-6">
                <Form.Label>End Shift Time</Form.Label>
                <Form.Control
                  type="time"
                  placeholder="End SHift Time"
                  value={formData.endShiftTime}
                  onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
                    handleFormChange("endShiftTime", e.target.value)
                  }
                  isInvalid={!!formErrors[`endShiftTime`]}
                />
                <Form.Control.Feedback type="invalid">
                  {formErrors[`endShiftTime`]}
                </Form.Control.Feedback>
              </div>
            </div>
          </Form.Group>

          <Form.Group>
            <div className="row mb-3">
              <div className="col-md-6">
                <Form.Label>Is Manager</Form.Label>
                <Form.Control
                  as="select"
                  value={formData.isManager}
                  onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
                    handleFormChange("isManager", e.target.value)
                  }
                >
                  <option value="true">Yes</option>
                  <option value="false">No</option>
                </Form.Control>
              </div>

              <div className="col-md-6">
                <Form.Label>Salary</Form.Label>
                <Form.Control
                  type="number"
                  placeholder="Salary"
                  value={formData.salary}
                  onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
                    handleFormChange("salary", e.target.value)
                  }
                  isInvalid={!!formErrors[`salary`]}
                />
                <Form.Control.Feedback type="invalid">
                  {formErrors[`salary`]}
                </Form.Control.Feedback>
              </div>
            </div>
          </Form.Group>
          <hr className="my-3 custom-line custom-line-notes" />
          <div className="mb-3">
            {/* Emp tasks */}
            {employeeTasks.map((task, index) => (
              <div key={index} className="mb-3">
                <Form.Group>
                  <div className="row mb-3">
                    <div className="col-md-10">
                      <Form.Label>Task {index + 1}</Form.Label>
                      <Form.Control
                        as="textarea"
                        placeholder="Enter the task"
                        rows={1}
                        value={task}
                        onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
                          handleTasksChange(index, e.target.value)
                        }
                        isInvalid={!!formErrors[`task${index}`]}
                      />
                      <Form.Control.Feedback type="invalid">
                        {formErrors[`task${index}`]}
                      </Form.Control.Feedback>
                    </div>
                    <div className="col">
                      <Button
                        style={{ marginTop: "33px" }}
                        variant="danger"
                        onClick={() => deleteTask(index)}
                      >
                        Delete Task
                      </Button>
                    </div>
                  </div>
                </Form.Group>
              </div>
            ))}
            <Button variant="primary" onClick={addNewTask}>
              Add Task
            </Button>
            <hr className="my-3 custom-line custom-line-notes" />
          </div>

          <div className="mb-3">
            {/* emp holidays */}
            {employeeHolidays.map((holiday, index) => (
              <div key={index} className="mb-3">
                <Form.Group>
                  <div className="row mb-3">
                    <div className="col-md-10">
                      <Form.Label>Holiday {index + 1}</Form.Label>
                      <Form.Control
                        as="textarea"
                        placeholder="Enter the holiday"
                        rows={1}
                        value={holiday}
                        onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
                          handleHolidaysChange(index, e.target.value)
                        }
                        isInvalid={!!formErrors[`holiday${index}`]}
                      />
                      <Form.Control.Feedback type="invalid">
                        {formErrors[`holiday${index}`]}
                      </Form.Control.Feedback>
                    </div>
                    <div className="col">
                      <Button
                        style={{ marginTop: "33px" }}
                        variant="danger"
                        onClick={() => deleteHoliday(index)}
                      >
                        Delete Holiday
                      </Button>
                    </div>
                  </div>
                </Form.Group>
              </div>
            ))}
            <Button variant="primary" onClick={addNewHoliday}>
              Add Holiday
            </Button>
            <hr className="my-3 custom-line custom-line-notes" />
          </div>

          <Button variant="primary" type="submit">
            {buttonName}
          </Button>
        </Form>
      </Modal.Body>
    </Modal>
  );
};

export default HandleEmployee;
