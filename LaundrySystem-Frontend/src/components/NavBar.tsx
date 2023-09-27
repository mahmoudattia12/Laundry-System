import React from "react";
import { Link } from "react-router-dom";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faUser,
  faFilePen,
  faListCheck,
  faIdCard,
  faUsersGear,
  faChartLine,
  faLeftLong,
} from "@fortawesome/free-solid-svg-icons";

interface NavBarProps {
  employeeName: string;
  laundryName: string;
  containerPage: string;
}

const NavBar = ({ employeeName, laundryName, containerPage }: NavBarProps) => {
  return (
    <div
      className="d-flex flex-column justify-content-between bg-primary text-light"
      style={{
        height: "100vh",
        width: "25%",
        borderTopRightRadius: "7px",
        borderBottomRightRadius: "7px",
      }}
    >
      <div className="p-3">
        <div
          className="mb-3"
          style={{ fontSize: "18px", color: "black", fontWeight: "bold" }}
        >
          <img
            src="../../public/washing-machine.png"
            alt="Laundry Icon"
            className="mr-2"
            style={{ marginRight: "8px" }}
            width="45"
            height="45"
          />
          {laundryName}
        </div>
        <div
          className="mb-3"
          style={{ fontSize: "18px", fontWeight: "bold", color: "black" }}
        >
          <FontAwesomeIcon
            icon={faUser}
            className="fs-2 mr-2"
            style={{ marginRight: "8px" }}
          />
          {employeeName}
        </div>
      </div>

      <div
        className="d-flex flex-column p-2"
        // style={{ backgroundColor: "#457cba" }}
      >
        <Link
          to={`/Orders?employeeName=${employeeName}&laundryName=${laundryName}`}
          className="btn m-0 p-2"
          style={{
            textAlign: "left",
            ...(containerPage === "orders" && { backgroundColor: "#73b0f5" }),
          }}
        >
          <FontAwesomeIcon
            icon={faFilePen}
            className="fs-4 mr-2"
            style={{ marginRight: "15px" }}
          />
          Orders
        </Link>
        <Link
          to={`/empInfo?employeeName=${employeeName}&laundryName=${laundryName}`}
          className="btn m-0 p-2"
          style={{
            textAlign: "left",
            ...(containerPage === "empInfo" && { backgroundColor: "#73b0f5" }),
          }}
        >
          <FontAwesomeIcon
            icon={faListCheck}
            className="fs-4 mr-2"
            style={{ marginRight: "15px" }}
          />
          Employees Info
        </Link>
        <Link
          to={`/employees?employeeName=${employeeName}&laundryName=${laundryName}`}
          className="btn m-0 p-2"
          style={{
            textAlign: "left",
            ...(containerPage === "empManagement" && {
              backgroundColor: "#73b0f5",
            }),
          }}
        >
          <FontAwesomeIcon
            icon={faUsersGear}
            className="fs-4 mr-2"
            style={{ marginRight: "15px" }}
          />
          Employees Management
        </Link>
        <Link
          to={`/customers?employeeName=${employeeName}&laundryName=${laundryName}`}
          className="btn m-0 p-2"
          style={{
            textAlign: "left",
            ...(containerPage === "customerManagement" && {
              backgroundColor: "#73b0f5",
            }),
          }}
        >
          <FontAwesomeIcon
            icon={faIdCard}
            className="fs-4 mr-2"
            style={{ marginRight: "15px" }}
          />
          Customers Management
        </Link>
        {/* <Link
          to="/statistics"
          className="btn m-0 p-2"
          style={{
            textAlign: "left",
            ...(containerPage === "statistics" && {
              backgroundColor: "#73b0f5",
            }),
          }}
        >
          <FontAwesomeIcon
            icon={faChartLine}
            className="fs-4 mr-2"
            style={{ marginRight: "15px" }}
          />
          Statistics
        </Link> */}
      </div>

      <div
        className="d-flex flex-column align-items-center p-3"
        style={{ marginBottom: "10vh", marginRight: "15px" }}
      >
        <Link
          to={`/employeeLogin?laundryName=${laundryName}`}
          className="btn btn-danger mb-2"
        >
          <FontAwesomeIcon
            icon={faLeftLong}
            className="fs-4 mr-2"
            style={{ marginRight: "10px" }}
          />
          Logout
        </Link>
      </div>
    </div>
  );
};

export default NavBar;
