import React, { useState } from "react";
import NavBar from "../../components/NavBar";
import HandleCustomer from "../../components/HandleCustomer";
import HandleOrder from "../../components/HandleOrder";
import { useLocation } from "react-router-dom";

const Orders = () => {
  const [showCreateOrderModal, setShowCreateOrderModal] = useState(false);
  //const [showAddCustomerModal, setAddCustomerModal] = useState(false);

  const location = useLocation();
  var { laundryName, empUserName } = location.state || {};

  const handleCloseCreateOrderModal = () => {
    setShowCreateOrderModal(false);
  };

  const handleShowCreateOrderModal = () => {
    setShowCreateOrderModal(true);
  };

  return (
    <>
      <NavBar employeeName={empUserName} laundryName={laundryName} />
      <div className="container mt-4">
        <div className="row">
          <div className="justify-content-start col-3 d-flex">
            <button
              className="btn btn-primary btn-block ms-0"
              // data-bs-toggle="modal"
              // data-bs-target="#createOrderModal"
              onClick={handleShowCreateOrderModal}
            >
              Create Order
            </button>
            <button
              className="btn btn-primary btn-block ms-1"
              data-bs-toggle="modal"
              data-bs-target="#addCustomerModal"
            >
              Add Customer
            </button>
          </div>
        </div>

        <div className="row mt-4">
          <div className="col-md-4 col-lg-3">
            <div className="input-group">
              <input
                type="text"
                className="form-control"
                placeholder="Search..."
              />
              <button className="btn btn-outline-secondary" type="button">
                Search
              </button>
            </div>
          </div>
          <div className="col-md-4 col-lg-3 mt-2 mt-md-0">
            <select className="form-select">
              <option value="">Filter by...</option>
              {/* Add filter options here */}
            </select>
          </div>
          <div className="col-md-4 col-lg-3 mt-2 mt-md-0">
            <select className="form-select">
              <option value="">Sort by...</option>
              {/* Add sorting options here */}
            </select>
          </div>
        </div>
        <div className="row mt-4">
          <div className="col">
            <h3>Orders List</h3>
            {/* Add the list of orders here */}
          </div>
        </div>
      </div>
      {/* Create Order Modal */}
      <HandleOrder
        show={showCreateOrderModal}
        onHide={handleCloseCreateOrderModal}
        laundryName={laundryName}
      />

      <HandleCustomer
        id="addCustomerModal"
        header="Add Customer"
        isAdd={true}
        laundryName={laundryName}
      />
    </>
  );
};

export default Orders;
