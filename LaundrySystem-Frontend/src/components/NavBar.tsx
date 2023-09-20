import React from "react";
import { Link } from "react-router-dom";

interface NavBarProps {
  employeeName: string;
  laundryName: string;
}

const NavBar = ({ employeeName, laundryName }: NavBarProps) => {
  return (
    <nav className="navbar navbar-expand-lg navbar-dark bg-primary">
      <div className="container-fluid">
        <Link className="navbar-brand" to="/">
          {laundryName}
        </Link>

        <div className="navbar-text">{employeeName}</div>

        <div className="ml-auto">
          <ul className="navbar-nav">
            <li className="nav-item">
              <Link className="nav-link" to="/orders">
                Orders
              </Link>
            </li>
            <li className="nav-item">
              <Link className="nav-link" to="/statistics">
                Statistics
              </Link>
            </li>
            <li className="nav-item">
              <Link className="nav-link" to="/tasks">
                Employee Tasks
              </Link>
            </li>
            <li className="nav-item">
              <Link className="nav-link" to="/customers">
                Customers Management
              </Link>
            </li>
            <li className="nav-item">
              <Link className="nav-link" to="/employees">
                Employees Management
              </Link>
            </li>
          </ul>
        </div>
      </div>
    </nav>
  );
};

export default NavBar;
