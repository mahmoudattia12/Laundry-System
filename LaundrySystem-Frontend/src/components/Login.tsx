import React, { useState } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faEye, faEyeSlash } from "@fortawesome/free-solid-svg-icons";
import Form from "./Form";

const LoginForm = () => {
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

    if (formData.userName.length < 3) {
      errors.userNameError = "User Name must be at least 3 characters";
      valid = false;
    } else {
      errors.userNameError = "";
    }

    // Password must be at least 8 characters and contain letters, digits, and symbols
    const passwordRegex =
      /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
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

  const handleLogin = (e: React.FormEvent) => {
    e.preventDefault();
    if (validateForm()) {
      // Form is valid, you can proceed with submission to the server.
      console.log(formData);
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
      title="Login"
      onSubmit={handleLogin}
      buttonText="Login"
    />
  );
};

export default LoginForm;
