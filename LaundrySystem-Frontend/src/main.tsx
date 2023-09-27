import React from "react";
import ReactDOM from "react-dom/client";
import App from "./App";
import "bootstrap/dist/css/bootstrap.css";
import "bootstrap/dist/css/bootstrap.min.css";
//import "bootstrap/dist/js/bootstrap.bundle.min.js";
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import LaundryLogin from "./pages/LaundryLogin";
import EmpLogin from "./pages/EmpLogin";
import EmpSignup from "./pages/EmpSignup";
import Orders from "./pages/home/Orders";
import Customers from "./pages/home/Customers";
import Employees from "./pages/home/Employees";
import EmployeeTasks from "./pages/home/EmployeeInfo";
import EmployeeInfo from "./pages/home/EmployeeInfo";

const router = createBrowserRouter([
  {
    path: "/",
    element: <App />,
  },
  {
    path: "laundryLogin",
    element: <LaundryLogin />,
  },
  {
    path: "employeeLogin",
    element: <EmpLogin />,
  },
  {
    path: "employeeSignup",
    element: <EmpSignup />,
  },
  {
    path: "Orders",
    element: <Orders />,
  },
  {
    path: "customers",
    element: <Customers />,
  },
  {
    path: "employees",
    element: <Employees />,
  },
  {
    path: "empInfo",
    element: <EmployeeInfo />,
  },
]);

ReactDOM.createRoot(document.getElementById("root") as HTMLElement).render(
  <React.StrictMode>
    <RouterProvider router={router} />
  </React.StrictMode>
);
