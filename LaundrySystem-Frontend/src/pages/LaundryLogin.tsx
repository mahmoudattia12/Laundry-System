import React, { useState } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faEye, faEyeSlash } from "@fortawesome/free-solid-svg-icons";
import Form from "../components/CardForm";
import axios from "axios";
import { useNavigate } from "react-router-dom";

const LaundryLogin = () => {
  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    name: "",
    password: "",
  });

  const [formErrors, setFormErrors] = useState({
    nameError: "",
    passwordError: "",
  });

  const [showPassword, setShowPassword] = useState(false); // State to show/hide password

  const validateForm = () => {
    let valid = true;
    const errors: any = {};

    if (formData.name.length < 3) {
      errors.nameError = "laundry Name must be at least 3 characters";
      valid = false;
    } else {
      errors.nameError = "";
    }

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

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (validateForm()) {
      console.log(formData);
      const toSent: any = {};
      toSent.name = formData.name.trim();
      toSent.password = formData.password;
      // setFormData(toSent);
      console.log(toSent);
      try {
        const response = await axios.post(
          "http://localhost:9080/laundry/login",
          toSent
        );
        console.log(response.data);
        if (response.data === "SUCCESS") {
          navigate("/employeeSignup", {
            state: {
              laundryName: toSent.name,
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
      label: "Laundry Name",
      type: "text",
      name: "name",
      value: formData.name,
      onChange: handleChange,
      error: formErrors.nameError,
    },
    {
      label: "Laundry Password",
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
      title="Laundry Login"
      onSubmit={handleSubmit}
      buttonText="Login"
      beforeLink="Laundry is not created? "
      insideLink="Sign Up"
      path="/"
      path2="/"
    />
  );
};

export default LaundryLogin;
