import React, { useState, useEffect } from "react";
import NavBar from "../../components/NavBar";
import FilterAndSort from "../../components/FilterAndSort";
import { useLocation } from "react-router-dom";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faPlus, faRotate } from "@fortawesome/free-solid-svg-icons";
import HandleCustomer from "../../components/HandleCustomer";
import axios from "axios";

export interface Customer {
  name: string;
  phoneNumber: string;
  email: string;
  address: string;
  totalPays: number;
  isGold: boolean;
}

const Customers = () => {
  const location = useLocation();
  const searchParams = new URLSearchParams(location.search);
  const holdLaundryName = searchParams.get("laundryName");
  const holdEmpName = searchParams.get("employeeName");
  const laundryName =
    holdLaundryName === null || holdLaundryName === undefined
      ? ""
      : holdLaundryName;
  const empUserName =
    holdEmpName === null || holdLaundryName === undefined ? "" : holdEmpName;
  const [selectedCustomer, setSelectedCustomer] = useState<Customer | null>(
    null
  );
  const [customers, setCustomers] = useState<Customer[]>([
    // {
    //   name: "mo salah",
    //   phone: "01204191992",
    //   email: "mosalah@gmail.com",
    //   address: "egypt-alex-mandra-mandra-mandra",
    //   totalPays: 210,
    //   isGold: true,
    // },
    // {
    //   name: "mo salah",
    //   phone: "01224191992",
    //   email: "mosalah@gmail.com",
    //   address: "egypt-alex-mandra-mandra-mandra",
    //   totalPays: 210,
    //   isGold: true,
    // },
    // {
    //   name: "mo salah",
    //   phone: "01234191992",
    //   email: "mosalah@gmail.com",
    //   address: "egypt-alex-mandra-mandra-mandra",
    //   totalPays: 210,
    //   isGold: true,
    // },
  ]);

  const handleCloseUpdateCustomerModal = () => {
    setSelectedCustomer(null);
  };

  const handleShowUpdateCustomerModal = (customer: Customer) => {
    setSelectedCustomer(customer);
  };

  const handleCustomerSuccessUpdate = (updatedCustomer: Customer) => {
    const prevID: string = updatedCustomer.phoneNumber;
    const prevCustomer = customers.filter(
      (customer) => customer.phoneNumber === prevID
    );
    updatedCustomer.totalPays = prevCustomer[0].totalPays;
    updatedCustomer.isGold = prevCustomer[0].isGold;
    const updatedCustomers = customers.map((customer) => {
      if (customer.phoneNumber === prevID) {
        return updatedCustomer;
      }
      return customer;
    });

    setCustomers(updatedCustomers);
  };

  const handleCustomerSuccessCreation = (newCustomer: Customer) => {
    const updatedCustomers = [...customers, newCustomer];

    setCustomers(updatedCustomers);
  };

  const getCustomersList = async () => {
    try {
      const response = await axios.get(
        `http://localhost:9080/customer/getAll/${laundryName}`
      );

      const customersData = response.data;
      customersAdapter(customersData);
    } catch (error) {
      console.error("Error fetching orders:", error);
      alert("Error fetching orders");
    }
  };

  const deleteCustomer = async (customerID: string) => {
    try {
      const response = await axios.delete(
        `http://localhost:9080/customer/delete/${customerID}/${laundryName}`
      );
      if (response.data === "SUCCESS") {
        const updatedCustomers = customers.filter(
          (customer) => customer.phoneNumber !== customerID
        );
        setCustomers(updatedCustomers);
      } else {
        alert(response.data);
      }
    } catch (error) {
      alert(error);
    }
  };

  const customersAdapter = (responseData: any[]) => {
    const toSet: any[] = [];
    for (const data of responseData) {
      const setCustomer: any = data;
      setCustomer.isGold = data.gold;
      toSet.push(setCustomer);
    }

    setCustomers(toSet);
  };

  useEffect(() => {
    getCustomersList();
  }, []);

  return (
    <div
      className="d-flex"
      style={{
        background:
          "linear-gradient(to top, #f3e7e9 0%, #e3eeff 99%, #e3eeff 100%)",
      }}
    >
      <NavBar
        employeeName={empUserName}
        laundryName={laundryName}
        containerPage="customerManagement"
      />

      <div className="container mt-4">
        <FilterAndSort
          entityName="cus"
          laundryName={laundryName}
          initialSelected="name"
          adapter={customersAdapter}
        />
        <hr className="my-3 custom-line" />
        <div className="row">
          <div className="justify-content-start col-2 d-flex">
            <button
              className="green-button btn-block ms-1"
              data-bs-toggle="modal"
              data-bs-target="#addCustomerModal"
              //style={{ backgroundColor: "green", borderColor: "green" }}
            >
              <FontAwesomeIcon
                icon={faPlus}
                className="fs-3 mr-2"
                style={{ marginRight: "8px" }}
              />
              Add Customer
            </button>
          </div>
        </div>
        <div className="row mt-4">
          <div className="col">
            <div className="row mt-1 md-1" style={{ marginBottom: "10px" }}>
              <div className="col-md-4 col-lg-3">
                <h2>Customers List</h2>
              </div>
              <div className="col-md-4 col-lg-2">
                <button
                  className="btn btn-primary"
                  type="button"
                  onClick={() => {
                    getCustomersList();
                  }}
                >
                  <FontAwesomeIcon
                    icon={faRotate}
                    className="fs-3 mr-2"
                    style={{ marginRight: "8px" }}
                  />
                  Reset List
                </button>
              </div>
            </div>

            <div className="table-container">
              <table
                className="table table-fixed"
                style={{ borderRadius: "10px" }}
              >
                <thead>
                  <tr>
                    <th>Name</th>
                    <th>Phone</th>
                    <th>Email</th>
                    <th>Address</th>
                    <th>Total Pays</th>
                    <th>Is Gold</th>
                    <th>Action</th>
                  </tr>
                </thead>
                <tbody>
                  {customers.map((customer) => (
                    <tr
                      key={customer.phoneNumber}
                      //   onClick={(e) => {
                      //     // Check if the click target is the delete button
                      //     // if (
                      //     //   (e.target as HTMLElement).tagName.toLowerCase() !==
                      //     //   "button"
                      //     // ) {
                      //     //   handleShowCustomerDetailsModal(customer);
                      //     // }
                      //   }}
                      //   style={{ cursor: "pointer" }}
                      //   className="hover-bg-gray"
                    >
                      <td>{customer.name}</td>
                      <td>{customer.phoneNumber}</td>
                      <td>{customer.email}</td>
                      <td>
                        {customer.address === ""
                          ? "Not Available"
                          : customer.address}
                      </td>
                      <td>{customer.totalPays}</td>
                      <td>{customer.isGold ? "Yes" : "No"}</td>

                      <td>
                        <button
                          className="btn btn-success btn-sm"
                          data-bs-toggle="modal"
                          data-bs-target="#updateCustomerModal"
                          style={{
                            marginRight: "15px",
                            backgroundColor: "green",
                            borderColor: "green",
                          }}
                          onClick={() => {
                            handleShowUpdateCustomerModal(customer);
                          }}
                        >
                          Update
                        </button>
                        <button
                          className="btn btn-danger btn-sm"
                          onClick={() => deleteCustomer(customer.phoneNumber)}
                        >
                          Delete
                        </button>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>
      <HandleCustomer
        id="addCustomerModal"
        header="Add Customer"
        isAdd={true}
        laundryName={laundryName}
        onClose={() => {}}
        onSuccess={handleCustomerSuccessCreation}
        isCustomersPage={true}
      />
      <HandleCustomer
        id="updateCustomerModal"
        header="Update Customer"
        isAdd={false}
        laundryName={laundryName}
        oldCustomer={selectedCustomer}
        onClose={handleCloseUpdateCustomerModal}
        onSuccess={handleCustomerSuccessUpdate}
        isCustomersPage={true}
      />
    </div>
  );
};

export default Customers;
