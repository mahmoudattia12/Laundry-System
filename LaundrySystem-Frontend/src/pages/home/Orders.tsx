import React, { useState, useEffect } from "react";
import NavBar from "../../components/NavBar";
import HandleCustomer from "../../components/HandleCustomer";
import HandleOrder, { OrderItem } from "../../components/HandleOrder";
import { useLocation } from "react-router-dom";
import OrderDetailsModal from "../../components/OrderDetailsModal";
import axios from "axios";

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

  const [orders, setOrders] = useState<Order[]>([
    // {
    //   id: 1,
    //   customerName: "Aly Said",
    //   customerPhone: "01204191992",
    //   customerAddress: "500 staskskanskaknaskak",
    //   alternatePhone: "01204191992",
    //   startDateTime: "2023-09-21 04:48:00",
    //   dueDateTime: "2023-09-22 04:48:00",
    //   totalPrice: 50.5,
    //   isPaid: false,
    //   isDelivery: true,
    //   currState: "in progress",
    //   items: [],
    //   notes: [],
    // },
  ]);

  const getOrdersList = async () => {
    try {
      const response = await axios.get(
        `http://localhost:9080/order/getAll/${laundryName}`
      );
      console.log(response.data);
      const ordersData = response.data;
      const toSet: any[] = [];
      for (const data of ordersData) {
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

      console.log("toSet", toSet);
      setOrders(toSet);
    } catch (error) {
      console.error("Error fetching orders:", error);
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
      }
    } catch (error) {
      console.error("Error fetching orders:", error);
      alert("Error fetching orders");
    }
  };

  useEffect(() => {
    getOrdersList();
  }, []); // Empty dependency array ensures the function is called once when the component mounts

  return (
    <div className="d-flex">
      {/* Left Sidebar (NavBar) */}
      <NavBar
        employeeName={empUserName}
        laundryName={laundryName}
        containerPage="orders"
      />

      {/* Right Content */}
      <div className="container mt-4">
        <div className="row">
          <div className="justify-content-start col-3 d-flex">
            <button
              className="btn btn-primary btn-block ms-0"
              // data-bs-target="#CreateOrderModal"
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
          <div className="col">
            <h2>Orders List</h2>
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
            <div className="table-container">
              <table className="table table-fixed">
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
                        // Check if the click target is the delete button
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
                          style={{ marginRight: "15px" }}
                          onClick={() => {
                            console.log(
                              "on click update selected order ID: ",
                              order.ID
                            );
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
      />
    </div>
  );
};

export default Orders;
