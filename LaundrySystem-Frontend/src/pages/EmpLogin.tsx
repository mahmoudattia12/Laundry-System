import React, { useState } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faEye, faEyeSlash } from "@fortawesome/free-solid-svg-icons";
import Form from "../components/CardForm";
import { useLocation, useNavigate } from "react-router-dom";
import axios from "axios";

const EmpLogin = () => {
  const navigate = useNavigate();

  const location = useLocation();
  const searchParams = new URLSearchParams(location.search);
  const laundryName = searchParams.get("laundryName");

  const [formData, setFormData] = useState({
    userName: "",
    password: "",
  });

  const [formErrors, setFormErrors] = useState({
    userNameError: "",
    passwordError: "",
  });

  const [showPassword, setShowPassword] = useState(false);

  const validateForm = () => {
    let valid = true;
    const errors: any = {};

    if (formData.userName.length < 3 || formData.userName.length > 20) {
      errors.userNameError =
        "User Name must be at least 3 and at most 20 characters";
      valid = false;
    } else {
      errors.userNameError = "";
    }

    // Password must be at least 8 characters and contain letters, digits, and symbols
    const passwordRegex =
      /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[!@#$%^&*()_+{}\[\]:;<>,.?~\\-])[A-Za-z\d!@#$%^&*()_+{}\[\]:;<>,.?~\\-]{8,}$/;
    if (!passwordRegex.test(formData.password)) {
      errors.passwordError =
        "Password must be at least 8 characters and contain letters, digits, and symbols";
      valid = false;
    } else {
      errors.passwordError = "";
    }
    setFormErrors(errors);
    return valid;
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value,
    });
  };

  const toggleShowPassword = () => {
    setShowPassword(!showPassword);
  };

  const handleLogin = async (e: React.FormEvent) => {
    e.preventDefault();
    if (validateForm()) {
      const toSent: any = {};
      toSent.userName = formData.userName.trim();
      toSent.password = formData.password;

      try {
        const response = await axios.post(
          `http://localhost:9080/emp/login?laundryName=${laundryName}`,
          toSent
        );

        if (response.data === "SUCCESS") {
          navigate("/Orders", {
            state: {
              laundryName: laundryName,
              empUserName: toSent.userName,
            },
          });
        } else {
          alert(response.data);
        }
      } catch (error) {
        console.error("Error:", error);
        alert(error);
      }
    }
  };

  const formFields = [
    {
      label: "User Name",
      type: "text",
      name: "userName",
      value: formData.userName,
      onChange: handleChange,
      error: formErrors.userNameError,
    },
    {
      label: "Password",
      type: showPassword ? "text" : "password",
      name: "password",
      value: formData.password,
      onChange: handleChange,
      error: formErrors.passwordError,
      passButton: (
        <button
          type="button"
          className="btn btn-outline-secondary"
          style={{ backgroundColor: "white", color: "black" }}
          onClick={toggleShowPassword}
        >
          {showPassword ? (
            <FontAwesomeIcon icon={faEyeSlash} />
          ) : (
            <FontAwesomeIcon icon={faEye} />
          )}
        </button>
      ),
    },
  ];

  return (
    <Form
      fields={formFields}
      title="Employee Login"
      onSubmit={handleLogin}
      buttonText="Login"
      beforeLink="Don't have an account? "
      insideLink="Sign Up"
      path={`/employeeSignup?laundryName=${laundryName}`}
      beforeLink2="Back to choose a laundry? "
      insideLink2="Laundry Sign Up"
      path2="/"
    />
  );
};

export default EmpLogin;
