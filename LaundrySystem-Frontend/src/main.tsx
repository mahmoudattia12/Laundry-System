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
]);

ReactDOM.createRoot(document.getElementById("root") as HTMLElement).render(
  <React.StrictMode>
    <RouterProvider router={router} />
  </React.StrictMode>
);
