import React, { useState } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faEye, faEyeSlash } from "@fortawesome/free-solid-svg-icons";
import Form from "./Form";

const SignupForm = () => {
  const [formData, setFormData] = useState({
    userName: "",
    password: "",
    email: "",
    phoneNumber: "",
  });

  const [formErrors, setFormErrors] = useState({
    userNameError: "",
    passwordError: "",
    phoneNumberError: "",
  });

  const [showPassword, setShowPassword] = useState(false); // State to show/hide password

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

    if (!/^[0][0-9]+$/.test(formData.phoneNumber)) {
      errors.phoneNumberError =
        "Phone Number must start with 0 and contain only digits";
      valid = false;
    } else if (
      formData.phoneNumber.length !== 9 &&
      formData.phoneNumber.length !== 11
    ) {
      errors.phoneNumberError = "Phone Number must be exactly 9 or 11 digits";
      valid = false;
    } else {
      errors.phoneNumberError = "";
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

  const handleSubmit = (e: React.FormEvent) => {
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
    {
      label: "Email",
      type: "email",
      name: "email",
      value: formData.email,
      onChange: handleChange,
    },
    {
      label: "Phone Number",
      type: "tel",
      name: "phoneNumber",
      value: formData.phoneNumber,
      onChange: handleChange,
      error: formErrors.phoneNumberError,
    },
  ];

  return (
    <Form
      fields={formFields}
      title="Signup"
      onSubmit={handleSubmit}
      buttonText="Signup"
    />
  );
};

export default SignupForm;
