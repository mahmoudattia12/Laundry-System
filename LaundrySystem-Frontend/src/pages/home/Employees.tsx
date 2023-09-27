import React, { useState, useEffect } from "react";
import { useLocation } from "react-router-dom";
import NavBar from "../../components/NavBar";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faRotate } from "@fortawesome/free-solid-svg-icons";
import FilterAndSort from "../../components/FilterAndSort";
import axios from "axios";
import EmployeeDetailsModal from "../../components/EmployeeDetailsModal";
import HandleEmployee from "../../components/HandleEmployee";

export interface Employee {
  userName: string;
  phoneNumber: string;
  email: string;
  isManager: boolean;
  salary?: number;
  startShiftTime?: string;
  endShiftTime?: string;
  tasks: string[];
  holidays: string[];
}

const Employees = () => {
  const location = useLocation();
  const searchParams = new URLSearchParams(location.search);
  const holdLaundryName = searchParams.get("laundryName");
  const holdEmpName = searchParams.get("employeeName");
  const laundryName =
    holdLaundryName === null || holdLaundryName === undefined
      ? ""
      : holdLaundryName;
  const empUserName =
    holdEmpName === null || holdLaundryName === undefined ? "" : holdEmpName;

  const [showEmployeeDetailsModal, setShowEmployeeDetailsModal] =
    useState(false);
  const [selectedEmployee, setSelectedEmployee] = useState<Employee | null>(
    null
  );
  const [showUpdateEmployeeModal, setShowUpdateEmployeeModal] = useState(false);

  const [employees, setEmployees] = useState<Employee[]>([
    // {
    //   userName: "Mahmoud Attia",
    //   phoneNumber: "01204191992",
    //   email: "mahmoud@gmail.com",
    //   isManager: false,
    //   tasks: [],
    //   holidays: [],
    // },
  ]);

  const handleShowEmployeeDetailsModal = (employee: Employee) => {
    setSelectedEmployee(employee);
    setShowEmployeeDetailsModal(true);
  };

  const handleCloseEmployeeDetailsModal = () => {
    setSelectedEmployee(null);
    setShowEmployeeDetailsModal(false);
  };

  const handleCloseUpdateEmployeeModal = () => {
    setSelectedEmployee(null);
    setShowUpdateEmployeeModal(false);
  };

  const handleShowUpdateEmployeeModal = (employee: Employee) => {
    setSelectedEmployee(employee);
    setShowUpdateEmployeeModal(true);
  };

  const handleEmployeeSuccessUpdate = (updatedEmployee: Employee) => {
    const prevID: string = updatedEmployee.userName;

    const updatedEmps = employees.map((emp) => {
      if (emp.userName === prevID) {
        return updatedEmployee;
      }
      return emp;
    });

    setEmployees(updatedEmps);
  };

  const getEmployeesList = async () => {
    try {
      const response = await axios.get(
        `http://localhost:9080/emp/getAll/${laundryName}/${empUserName}/emps`
      );

      if (
        response.data !== "" &&
        response.data !== null &&
        response.data.length === 0
      ) {
        alert("NOT ALLOWED! this page can be accessed only by managers.");
      } else {
        const data = response.data;
        empAdapter(data);
      }
    } catch (error) {
      console.error("Error fetching Employees:", error);
      alert("Error fetching Employees");
    }
  };

  const deleteEmployee = async (userName: string) => {
    try {
      const response = await axios.delete(
        `http://localhost:9080/emp/delete/${empUserName}/${userName}/${laundryName}`
      );
      if (response.data === "SUCCESS") {
        const updatedEmps = employees.filter(
          (emp) => emp.userName !== userName
        );
        setEmployees(updatedEmps);
      } else {
        alert(response.data);
      }
    } catch (error) {
      alert(error);
    }
  };

  const empAdapter = (list: any[]) => {
    const toSet: any[] = [];
    for (const data of list) {
      const setEmp: any = {};
      setEmp.userName = data.userName;
      setEmp.phoneNumber = data.phoneNumber;
      setEmp.email = data.email;
      setEmp.isManager = data.manager;
      setEmp.tasks = data.tasksMessages ? data.tasksMessages : [];
      setEmp.holidays = data.holidayMessages ? data.holidayMessages : [];
      setEmp.salary = data.salary ? data.salary : null;
      setEmp.startShiftTime = data.startShiftTime ? data.startShiftTime : null;
      setEmp.endShiftTime = data.endShiftTime ? data.endShiftTime : null;
      toSet.push(setEmp);
    }

    setEmployees(toSet);
  };

  useEffect(() => {
    getEmployeesList();
  }, []);

  return (
    <div
      className="d-flex"
      style={{
        background:
          "linear-gradient(to top, #f3e7e9 0%, #e3eeff 99%, #e3eeff 100%)",
      }}
    >
      <NavBar
        employeeName={empUserName}
        laundryName={laundryName}
        containerPage="empManagement"
      />
      <div className="container mt-4">
        <FilterAndSort
          entityName="emp"
          laundryName={laundryName}
          initialSelected="userName"
          adapter={empAdapter}
        />
        <hr className="my-3 custom-line" />

        <div className="row mt-4">
          <div className="col">
            <div className="row mt-1 md-1" style={{ marginBottom: "10px" }}>
              <div className="col-md-4 col-lg-3">
                <h2>Employees List</h2>
              </div>
              <div className="col-md-4 col-lg-2">
                <button
                  className="btn btn-primary"
                  type="button"
                  onClick={() => {
                    getEmployeesList();
                  }}
                >
                  <FontAwesomeIcon
                    icon={faRotate}
                    className="fs-3 mr-2"
                    style={{ marginRight: "8px" }}
                  />
                  Reset List
                </button>
              </div>
            </div>

            <div className="table-container">
              <table
                className="table table-fixed"
                style={{ borderRadius: "10px" }}
              >
                <thead>
                  <tr>
                    <th>User Name</th>
                    <th>Phone</th>
                    <th>Email</th>
                    <th>Start Shift Time</th>
                    <th>End Shift Time</th>
                    <th>Is Manager</th>
                    <th>Action</th>
                  </tr>
                </thead>
                <tbody>
                  {employees.map((emp) => (
                    <tr
                      key={emp.userName}
                      onClick={(e) => {
                        if (
                          (e.target as HTMLElement).tagName.toLowerCase() !==
                          "button"
                        ) {
                          handleShowEmployeeDetailsModal(emp);
                        }
                      }}
                      style={{ cursor: "pointer" }}
                      className="hover-bg-gray"
                    >
                      <td>{emp.userName}</td>
                      <td>{emp.phoneNumber}</td>
                      <td>{emp.email}</td>
                      <td>
                        {emp.startShiftTime ? emp.startShiftTime : "_______"}
                      </td>
                      <td>{emp.endShiftTime ? emp.endShiftTime : "_______"}</td>
                      <td>{emp.isManager ? "Yes" : "No"}</td>

                      <td>
                        <button
                          className="btn btn-success btn-sm"
                          data-bs-toggle="modal"
                          data-bs-target="#updateCustomerModal"
                          style={{
                            marginRight: "15px",
                            backgroundColor: "green",
                            borderColor: "green",
                          }}
                          onClick={() => {
                            handleShowUpdateEmployeeModal(emp);
                          }}
                        >
                          Update
                        </button>
                        <button
                          className="btn btn-danger btn-sm"
                          onClick={() => deleteEmployee(emp.userName)}
                        >
                          Delete
                        </button>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>
      <EmployeeDetailsModal
        show={showEmployeeDetailsModal}
        employee={selectedEmployee}
        onHide={handleCloseEmployeeDetailsModal}
      />
      <HandleEmployee
        show={showUpdateEmployeeModal}
        onHide={handleCloseUpdateEmployeeModal}
        laundryName={laundryName}
        currEmployee={empUserName}
        oldEmployee={selectedEmployee}
        headerTitle="Update Employee"
        buttonName="Update"
        onSuccess={handleEmployeeSuccessUpdate}
      />
    </div>
  );
};

export default Employees;
