import React from "react";
import { Modal } from "react-bootstrap";
import { Employee } from "../pages/home/Employees";

interface EmployeeDetailsModalProps {
  show: boolean;
  employee: Employee | null;
  onHide: () => void;
}

const EmployeeDetailsModal = ({
  show,
  employee,
  onHide,
}: EmployeeDetailsModalProps) => {
  return (
    <Modal show={show} onHide={onHide} size="lg">
      <Modal.Header closeButton>
        <Modal.Title>Employee Details</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        {employee && (
          <div>
            <p className="fs-5" style={{ color: "#2502b5" }}>
              <span style={{ fontWeight: "bold", color: "black" }}>
                User Name:
              </span>{" "}
              {employee.userName}
            </p>

            <p className="fs-5" style={{ color: "#2502b5" }}>
              <span style={{ fontWeight: "bold", color: "black" }}>Phone:</span>{" "}
              {employee.phoneNumber}
            </p>

            <p className="fs-5" style={{ color: "#2502b5" }}>
              <span style={{ fontWeight: "bold", color: "black" }}>Email:</span>{" "}
              {employee.email}
            </p>

            <div className="row">
              {employee.startShiftTime && (
                <div className="col">
                  <p className="fs-5" style={{ color: "#2502b5" }}>
                    <span style={{ fontWeight: "bold", color: "black" }}>
                      Start Shift Time:
                    </span>{" "}
                    {employee.startShiftTime}
                  </p>
                </div>
              )}
              {employee.endShiftTime && (
                <div className="col">
                  <p className="fs-5" style={{ color: "#2502b5" }}>
                    <span style={{ fontWeight: "bold", color: "black" }}>
                      End Shift Time:
                    </span>{" "}
                    {employee.endShiftTime}
                  </p>
                </div>
              )}
            </div>
            <div className="row">
              <div className="col">
                <p className="fs-5" style={{ color: "#2502b5" }}>
                  <span style={{ fontWeight: "bold", color: "black" }}>
                    Is Manager:
                  </span>{" "}
                  {employee.isManager ? "Yes" : "No"}
                </p>
              </div>
              {employee.salary && (
                <div className="col">
                  <p className="fs-5" style={{ color: "#2502b5" }}>
                    <span style={{ fontWeight: "bold", color: "black" }}>
                      Salary:
                    </span>{" "}
                    {employee.salary}
                  </p>
                </div>
              )}
            </div>

            <hr className="my-3" />

            {employee.tasks.length > 0 && (
              <>
                <h5 style={{ fontWeight: "bold" }}>Tasks:</h5>
                <ul>
                  {employee.tasks.map((task, index) => (
                    <li key={index}>{task}</li>
                  ))}
                </ul>
              </>
            )}

            {employee.holidays.length > 0 && (
              <>
                <h5 style={{ fontWeight: "bold" }}>Holidays:</h5>
                <ul>
                  {employee.holidays.map((holiday, index) => (
                    <li key={index}>{holiday}</li>
                  ))}
                </ul>
              </>
            )}
          </div>
        )}
      </Modal.Body>
      <Modal.Footer className="justify-content-center">
        <button className="btn btn-secondary" onClick={onHide}>
          Close
        </button>
      </Modal.Footer>
    </Modal>
  );
};

export default EmployeeDetailsModal;
