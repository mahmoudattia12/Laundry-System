import React, { useEffect, useState, useRef } from "react";
import Form from "./Form";
import axios from "axios";
import * as bootstrap from "bootstrap";

interface HandleCustomerProps {
  id: string;
  header: string;
  isAdd: boolean; //to distinguish between add/update customer
  laundryName: string;
}

const HandleCustomer = ({
  id,
  header,
  isAdd,
  laundryName,
}: HandleCustomerProps) => {
  // useEffect(() => {
  //   resetForm();
  //   // return () => {
  //   //   console.log("Component is unmounted");
  //   // };
  // }, []);

  const [customerData, setFormData] = useState({
    phoneNumber: "",
    email: "",
    name: "",
    address: "",
  });

  const [validationErrors, setValidationErrors] = useState({
    customerNameError: "",
    phoneNumberError: "",
    addressError: "",
  });

  const resetForm = () => {
    setFormData({
      phoneNumber: "",
      email: "",
      name: "",
      address: "",
    });
    setValidationErrors({
      customerNameError: "",
      phoneNumberError: "",
      addressError: "",
    });
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData({
      ...customerData,
      [name]: value,
    });
  };

  const validateForm = () => {
    console.log("hi from validate form");
    let valid = true;
    const errors: any = {};

    if (customerData.name.length < 3) {
      errors.customerNameError = "Name must be at least 3 characters";
      valid = false;
    } else {
      errors.customerNameError = "";
    }

    if (customerData.address !== "" && customerData.address.length < 4) {
      errors.addressError = "Address must be at least 4 characters";
      valid = false;
    } else {
      errors.addressError = "";
    }

    if (
      customerData.phoneNumber.length !== 9 &&
      customerData.phoneNumber.length !== 11
    ) {
      errors.phoneNumberError = "Phone Number must be exactly 9 or 11 digits";
      valid = false;
    } else if (!/^[0][0-9]+$/.test(customerData.phoneNumber)) {
      errors.phoneNumberError =
        "Phone Number must start with 0 and contain only digits";
      valid = false;
    } else {
      errors.phoneNumberError = "";
    }

    setValidationErrors(errors);
    return valid;
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (validateForm()) {
      console.log(customerData);
      const toSent: any = {};
      toSent.name = customerData.name.trim();
      toSent.address = customerData.address.trim();
      toSent.email = customerData.email.trim();
      toSent.phoneNumber = customerData.phoneNumber;
      console.log(toSent);

      try {
        const response = await axios.post(
          `http://localhost:9080/customer/add?laundryName=${laundryName}`,
          toSent
        );
        console.log(response.data);
        if (response.data === "SUCCESS") {
          alert(response.data);
          const modalElement = document.getElementById(id);
          if (modalElement) {
            const modal = bootstrap.Modal.getInstance(modalElement);
            if (modal) {
              modal.hide();
            }
          }

          // Handle the background (remove modal-open class)
          document.body.classList.remove("modal-open");
          const modalBackdrop = document.querySelector(".modal-backdrop");
          if (modalBackdrop) {
            modalBackdrop.remove();
          }
          resetForm();
        } else {
          alert(response.data);
        }
      } catch (error) {
        console.error("Error:", error);
        alert(error);
      }
    }
  };

  const addFields = [
    {
      label: "Customer Name",
      type: "text",
      name: "name",
      value: customerData.name,
      onChange: handleChange,
      error: validationErrors.customerNameError,
    },
    {
      label: "Phone Number",
      type: "tel",
      name: "phoneNumber",
      value: customerData.phoneNumber,
      onChange: handleChange,
      error: validationErrors.phoneNumberError,
    },
    {
      label: "Email",
      type: "email",
      name: "email",
      value: customerData.email,
      onChange: handleChange,
    },
    {
      label: "Address",
      type: "text",
      name: "address",
      value: customerData.address,
      onChange: handleChange,
      error: validationErrors.addressError,
      notRequired: true,
    },
  ];

  return (
    <>
      <div
        className="modal fade"
        id={id}
        tabIndex={-1}
        aria-labelledby={id + "Label"}
        aria-hidden="true"
      >
        <div className="modal-dialog">
          <div className="modal-content">
            <div className="modal-header">
              <h5 className="modal-title" id={id + "Label"}>
                {header}
              </h5>
              <button
                type="button"
                className="btn-close btn"
                data-bs-dismiss="modal"
                aria-label="Close"
                onClick={() => {
                  resetForm();
                }}
                //   style={{ backgroundColor: "rgb(255, 0, 0)" }}
              ></button>
            </div>
            <div className="modal-body">
              <Form
                fields={addFields}
                onSubmit={handleSubmit}
                buttonText="Add Customer"
                path="/"
                path2="/"
              />
            </div>
          </div>
        </div>
      </div>
    </>
  );
};

export default HandleCustomer;
