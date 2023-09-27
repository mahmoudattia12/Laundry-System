import React, { useState, useEffect } from "react";
import NavBar from "../../components/NavBar";
import HandleCustomer from "../../components/HandleCustomer";
import HandleOrder, { OrderItem } from "../../components/HandleOrder";
import { useLocation } from "react-router-dom";
import OrderDetailsModal from "../../components/OrderDetailsModal";
import axios from "axios";
import FilterAndSort from "../../components/FilterAndSort";
import { Customer } from "./Customers";

import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faFilePen, faPlus, faRotate } from "@fortawesome/free-solid-svg-icons";

export interface Order {
  ID: number;
  customerName: string;
  customerPhone: string;
  customerAddress: string;
  alternatePhone: string;
  startDateTime: string;
  dueDateTime: string;
  totalPrice: number;
  isPaid: boolean;
  isDelivery: boolean;
  currState: string;
  items: OrderItem[];
  notes: string[];
}

const Orders = () => {
  const [showCreateOrderModal, setShowCreateOrderModal] = useState(false);
  const [showUpdateOrderModal, setShowUpdateOrderModal] = useState(false);
  const [selectedOrder, setSelectedOrder] = useState<Order | null>(null);
  const [showOrderDetailsModal, setShowOrderDetailsModal] = useState(false);

  const location = useLocation();
  var { laundryName, empUserName } = location.state || {};

  const searchParams = new URLSearchParams(location.search);
  const holdLaundryName = searchParams.get("laundryName");
  const holdEmpName = searchParams.get("employeeName");

  // if (laundryName === "") {
  laundryName =
    holdLaundryName === null || holdLaundryName === undefined
      ? laundryName
      : holdLaundryName;
  // }
  // if (empUserName === "") {
  empUserName =
    holdEmpName === null || holdEmpName === undefined
      ? empUserName
      : holdEmpName;
  // }

  const [orders, setOrders] = useState<Order[]>([]);

  const handleOrderSuccessCreation = (toAddOrder: Order) => {
    const toSet: any[] = orders;
    toSet.push(toAddOrder);
    setOrders(toSet);
  };

  const handleOrderSuccessUpdate = (updatedOrder: Order) => {
    const prevID: number = updatedOrder.ID;

    const updatedOrders = orders.map((order) => {
      if (order.ID === prevID) {
        return updatedOrder;
      }
      return order;
    });

    setOrders(updatedOrders);
  };

  const handleCloseCreateOrderModal = () => {
    setShowCreateOrderModal(false);
  };

  const handleShowCreateOrderModal = () => {
    setShowCreateOrderModal(true);
  };

  const handleCloseUpdateOrderModal = () => {
    setSelectedOrder(null);
    setShowUpdateOrderModal(false);
  };

  const handleShowUpdateOrderModal = (order: Order) => {
    setSelectedOrder(order);
    setShowUpdateOrderModal(true);
  };

  const handleShowOrderDetailsModal = (order: Order) => {
    setSelectedOrder(order);
    setShowOrderDetailsModal(true);
  };

  const handleCloseOrderDetailsModal = () => {
    setSelectedOrder(null);
    setShowOrderDetailsModal(false);
  };

  const getOrdersList = async () => {
    try {
      const response = await axios.get(
        `http://localhost:9080/order/getAll/${laundryName}`
      );

      const ordersData = response.data;
      ordersAdapter(ordersData);
    } catch (error) {
      alert("Error fetching orders");
    }
  };

  const deleteOrder = async (orderID: number) => {
    try {
      const response = await axios.delete(
        `http://localhost:9080/order/delete/${orderID}`
      );
      if (response.data === "SUCCESS") {
        const updatedOrders = orders.filter((order) => order.ID !== orderID);
        setOrders(updatedOrders);
      } else {
        alert(response.data);
      }
    } catch (error) {
      alert(error);
    }
  };

  const ordersAdapter = (responseData: any[]) => {
    const toSet: any[] = [];
    for (const data of responseData) {
      const setOrder: any = {};
      setOrder.ID = data.order.id;
      setOrder.customerName = data.order.customer.name;
      setOrder.customerPhone = data.order.customer.phoneNumber;
      setOrder.customerAddress = data.order.customer.address;
      setOrder.alternatePhone = data.order.alternatePhone;
      setOrder.startDateTime = data.order.startDate.replace("T", " ");
      setOrder.dueDateTime = data.order.endDate.replace("T", " ");
      setOrder.totalPrice = data.order.totalPrice;
      setOrder.isPaid = data.order.paid;
      setOrder.isDelivery = data.order.delivery;
      setOrder.currState = data.order.currState;
      setOrder.items = data.orderItems;
      setOrder.notes = data.order.notesMessages;
      toSet.push(setOrder);
    }

    setOrders(toSet);
  };

  useEffect(() => {
    getOrdersList();
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
        containerPage="orders"
      />

      <div className="container mt-4">
        <FilterAndSort
          entityName="ord"
          laundryName={laundryName}
          initialSelected="customerName"
          adapter={ordersAdapter}
        />

        <hr className="my-3 custom-line" />
        <div className="row">
          <div className="justify-content-start col-3 d-flex">
            <button
              className="green-button btn-block ms-0"
              // style={{
              //   backgroundColor: "green",
              //   borderColor: "green",
              //   transition: "background-color 0.9s",
              // }}
              // data-bs-target="#CreateOrderModal"
              onClick={handleShowCreateOrderModal}
            >
              <FontAwesomeIcon
                icon={faFilePen}
                className="fs-3 mr-2"
                style={{ marginRight: "8px" }}
              />
              Create Order
            </button>
            <button
              className="green-button btn-block ms-1"
              data-bs-toggle="modal"
              data-bs-target="#addCustomerModal"
              // style={{
              //   backgroundColor: "green",
              //   borderColor: "green",
              //   transition: "background-color 0.3s",
              // }}
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
                <h2>Orders List</h2>
              </div>
              <div className="col-md-4 col-lg-2">
                <button
                  className="btn btn-primary"
                  type="button"
                  onClick={() => {
                    getOrdersList();
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
                    <th>Customer Name</th>
                    <th>Customer Phone</th>
                    <th>start Date & Time</th>
                    <th>Due Date & Time</th>
                    <th>Total Price</th>
                    <th>Is Paid</th>
                    <th>Is Delivery</th>
                    <th>Curr State</th>
                    <th>Action</th>
                  </tr>
                </thead>
                <tbody>
                  {orders.map((order) => (
                    <tr
                      key={order.ID}
                      onClick={(e) => {
                        if (
                          (e.target as HTMLElement).tagName.toLowerCase() !==
                          "button"
                        ) {
                          handleShowOrderDetailsModal(order);
                        }
                      }}
                      style={{ cursor: "pointer" }}
                      className="hover-bg-gray"
                    >
                      <td>{order.customerName}</td>
                      <td>{order.customerPhone}</td>
                      <td>{order.startDateTime}</td>
                      <td>{order.dueDateTime}</td>
                      <td>{order.totalPrice}</td>
                      <td>{order.isPaid ? "Yes" : "No"}</td>
                      <td>{order.isDelivery ? "Yes" : "No"}</td>
                      <td>{order.currState}</td>
                      <td>
                        <button
                          className="btn btn-success btn-sm"
                          // data-bs-target="#UpdateOrderModal"
                          //style={{ marginRight: "15px" }}
                          style={{
                            marginRight: "15px",
                            backgroundColor: "green",
                            borderColor: "green",
                          }}
                          onClick={() => {
                            handleShowUpdateOrderModal(order);
                          }}
                        >
                          Update
                        </button>
                        <button
                          className="btn btn-danger btn-sm"
                          onClick={() => deleteOrder(order.ID)}
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
      {/* Update Order Modal */}
      <HandleOrder
        // id="UpdateOrderModal"
        show={showUpdateOrderModal}
        onHide={handleCloseUpdateOrderModal}
        laundryName={laundryName}
        isUpdate={true}
        oldOrder={selectedOrder}
        headerTitle="Update Order"
        buttonName="Update"
        onSuccess={handleOrderSuccessUpdate}
      />
      {/* Create Order Modal */}
      <HandleOrder
        // id="CreateOrderModal"
        show={showCreateOrderModal}
        onHide={handleCloseCreateOrderModal}
        laundryName={laundryName}
        isUpdate={false}
        headerTitle="Create Order"
        buttonName="Submit"
        onSuccess={handleOrderSuccessCreation}
      />

      {/* Order Details Modal */}
      <OrderDetailsModal
        show={showOrderDetailsModal}
        order={selectedOrder}
        onHide={handleCloseOrderDetailsModal}
      />

      <HandleCustomer
        id="addCustomerModal"
        header="Add Customer"
        isAdd={true}
        laundryName={laundryName}
        onClose={() => {}}
        onSuccess={(customer: Customer) => {}}
        isCustomersPage={false}
      />
    </div>
  );
};

export default Orders;
