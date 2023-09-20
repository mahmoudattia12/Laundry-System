import { useState } from "react";
import reactLogo from "./assets/react.svg";
import "./App.css";
import EmpSignup from "./pages/EmpSignup";
import EmpLogin from "./pages/EmpLogin";
import LaundrySignup from "./pages/LaundrySignup";
import LaundryLogin from "./pages/LaundryLogin";
import NavBar from "./components/NavBar";
import Orders from "./pages/home/Orders";
function App() {
  return <LaundrySignup />;
}

export default App;
