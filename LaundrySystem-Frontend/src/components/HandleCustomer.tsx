import React, { useEffect, useState, useRef } from "react";
import Form from "./Form";
import axios from "axios";
import * as bootstrap from "bootstrap";
import { Customer } from "../pages/home/Customers";

interface HandleCustomerProps {
  id: string;
  header: string;
  isAdd: boolean; //to distinguish between add/update customer
  laundryName: string;
  oldCustomer?: Customer | null;
  onClose: () => void;
  onSuccess: (customer: Customer) => void;
  isCustomersPage: boolean;
}

const HandleCustomer = ({
  id,
  header,
  isAdd,
  laundryName,
  oldCustomer,
  onClose,
  onSuccess,
  isCustomersPage,
}: HandleCustomerProps) => {
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

  useEffect(() => {
    if (!isAdd && oldCustomer) {
      console.log("hi from update use effect");
      console.log("old customer", oldCustomer);
      setFormData({
        phoneNumber: "",
        email: oldCustomer.email,
        name: oldCustomer.name,
        address: oldCustomer.address,
      });
    }
  }, [!isAdd, oldCustomer]);

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
    console.log("isUpdate ", !isAdd);
    let valid = true;
    const errors: any = {};

    if (customerData.name.length < 3 || customerData.name.length > 20) {
      errors.customerNameError =
        "Name must be at least 3 and at most 20 characters";
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

    if (!isAdd) {
      errors.phoneNumberError = "";
    } else if (
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
    console.log("is valid: ", validateForm());
    if (validateForm()) {
      console.log(customerData);
      const toSend: any = {};
      toSend.name = customerData.name.trim();
      toSend.address = customerData.address.trim();
      toSend.email = customerData.email.trim();
      if (isAdd) {
        console.log("hi from add is customer page >>>> ", isCustomersPage);
        toSend.phoneNumber = customerData.phoneNumber;
        console.log(toSend);

        try {
          const response = await axios.post(
            `http://localhost:9080/customer/add?laundryName=${laundryName}`,
            toSend
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
            document.body.classList.remove("modal-open");
            const modalBackdrop = document.querySelector(".modal-backdrop");
            if (modalBackdrop) {
              modalBackdrop.remove();
            }
            if (isCustomersPage) {
              const toAdd: Customer = {
                name: toSend.name,
                phoneNumber: toSend.phoneNumber,
                email: toSend.email,
                address: toSend.address,
                totalPays: 0,
                isGold: false,
              };
              console.log("hi just before using the onSuccess to send", toAdd);
              onSuccess(toAdd);
            }

            resetForm();
          } else if (
            response.data === "Laundry Not Found" ||
            response.data === "Already Exists"
          ) {
            alert(response.data);
          } else {
            alert(
              "phone & email can't be duplicated from more than one customer"
            );
          }
        } catch (error) {
          console.error("Error:", error);
          alert(error);
        }
      } else {
        console.log("update customer is clicked and validated");
        console.log(customerData);
        toSend.phoneNumber = oldCustomer?.phoneNumber;
        console.log("toSend", toSend);

        if (toSend.phoneNumber) {
          try {
            const response = await axios.put(
              `http://localhost:9080/customer/update/${toSend.phoneNumber}`,
              toSend
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
              document.body.classList.remove("modal-open");
              const modalBackdrop = document.querySelector(".modal-backdrop");
              if (modalBackdrop) {
                modalBackdrop.remove();
              }

              onSuccess(toSend);
              resetForm();
            } else {
              alert(response.data);
            }
          } catch (error) {
            console.error("Error:", error);
            alert(error);
          }
        }
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

  const updateFields = [
    {
      label: "Customer Name",
      type: "text",
      name: "name",
      value: customerData.name,
      onChange: handleChange,
      error: validationErrors.customerNameError,
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
                  !isAdd && onClose();
                }}
              ></button>
            </div>
            <div className="modal-body">
              <Form
                fields={isAdd ? addFields : updateFields}
                onSubmit={handleSubmit}
                buttonText={isAdd ? "Add Customer" : "Update Customer"}
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
