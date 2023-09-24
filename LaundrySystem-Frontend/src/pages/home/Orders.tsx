import React, { useState, useEffect } from "react";
import NavBar from "../../components/NavBar";
import HandleCustomer from "../../components/HandleCustomer";
import HandleOrder, { OrderItem } from "../../components/HandleOrder";
import { useLocation } from "react-router-dom";
import OrderDetailsModal from "../../components/OrderDetailsModal";
import axios from "axios";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faMagnifyingGlass,
  faSort,
  faFilter,
  faFilePen,
  faPlus,
  faRotate,
} from "@fortawesome/free-solid-svg-icons";

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
  const [selectedFilter, setSelectedFilter] = useState("customerName");
  const [toMeetFilter, setToMeetFilter] = useState("");
  const [toMeetSearch, setToMeetSearch] = useState("");
  const [selectedSort, setSelectedSort] = useState("customerName");
  const [selectedSortingOrder, setSelectedSortingOrder] = useState("asc");

  const location = useLocation();
  var { laundryName, empUserName } = location.state || {};

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
      console.log(response.data);
      const ordersData = response.data;
      ordersAdapter(ordersData);
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

  const filterOrders = async (isFilter: boolean) => {
    try {
      var criteria, toMeet;
      isFilter
        ? ((criteria = selectedFilter), (toMeet = toMeetFilter))
        : ((criteria = "search"), (toMeet = toMeetSearch));

      if (criteria === "isDelivery" || criteria === "isPaid") {
        if (toMeet.toLocaleLowerCase() === "yes") setToMeetFilter("true");
        else if (toMeet.toLocaleLowerCase() === "no") setToMeetFilter("false");
      }

      const response = await axios.get(
        `http://localhost:9080/filterEntity/ord/${criteria}/${toMeet}/${laundryName}`
      );

      console.log("Filtered Orders:", response.data);

      ordersAdapter(response.data);
    } catch (error) {
      console.error("Error filtering orders:", error);
      alert("Error filtering orders");
    }
  };
  const sortOrders = async () => {
    try {
      const isAsc = selectedSortingOrder === "asc" ? true : false;
      const response = await axios.get(
        `http://localhost:9080/sortEntity/ord/${selectedSort}/${isAsc}/${laundryName}`
      );
      console.log("sorted data", response.data);
      ordersAdapter(response.data);
    } catch (error) {
      console.error("Error sorting orders:", error);
      alert("Error sorting orders");
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
    console.log("toSet from orders adapter", toSet);
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
      {/* Left Sidebar (NavBar) */}
      <NavBar
        employeeName={empUserName}
        laundryName={laundryName}
        containerPage="orders"
      />

      {/* Right Content */}
      <div className="container mt-4">
        <div>
          <div className="row">
            <div className="col-md-4 col-lg-5">
              <div className="input-group">
                <input
                  type="text"
                  className="form-control"
                  placeholder="Search..."
                  value={toMeetSearch}
                  onChange={(e) => setToMeetSearch(e.target.value)}
                />
                <button
                  className="btn btn-outline-secondary"
                  type="button"
                  onClick={() => {
                    filterOrders(false);
                  }}
                >
                  <FontAwesomeIcon
                    icon={faMagnifyingGlass}
                    className="fs-3 mr-2"
                    style={{ marginRight: "8px" }}
                  />
                </button>
              </div>
            </div>
          </div>

          <div className="row mt-4">
            <div
              className="col-md-4 col-lg-1 "
              style={{
                verticalAlign: "center",
                marginRight: "-20px",
                marginTop: "5px",
                alignContent: "center",
              }}
            >
              <span className="mt-2">Filter by:</span>
            </div>
            <div
              className="col-md-4 col-lg-1 mt-2 mt-md-0"
              style={{ margin: "0", padding: "2px", minWidth: "170px" }}
            >
              <select
                className="form-select"
                value={selectedFilter}
                onChange={(e) => setSelectedFilter(e.target.value)}
              >
                <option value="customerName">Customer Name</option>
                <option value="customerPhone">Customer Phone</option>
                <option value="alternatePhone">Alternate Phone</option>
                {/* <option value="startDate">Start Date</option>
                <option value="endDate">End Date</option> better in searching*/}
                <option value="price">Total Price</option>
                <option value="isDelivery">Is Delivery</option>
                <option value="isPaid">Is Paid</option>
                <option value="currState">Current State</option>
              </select>
            </div>
            <div
              className="col-md-4 col-lg-3 mt-2 mt-md-0"
              style={{ margin: "0", padding: "2px" }}
            >
              <input
                type="text"
                className="form-control"
                placeholder={
                  selectedFilter === "startDate"
                    ? "YYYY-MM-DD HH:mm:ss"
                    : selectedFilter === "endDate"
                    ? "YYYY-MM-DD HH:mm:ss"
                    : selectedFilter === "isDelivery"
                    ? "yes/no"
                    : selectedFilter === "isPaid"
                    ? "yes/no"
                    : selectedFilter === "currState"
                    ? "new/inprogress/finished/delivered"
                    : "Enter filter value..."
                }
                value={toMeetFilter}
                onChange={(e) => setToMeetFilter(e.target.value)}
              />
            </div>
            <div
              className="col-md-2 col-lg-1 mt-2 mt-md-0"
              style={{ margin: "0", padding: "2px" }}
            >
              <button
                className="btn btn-primary"
                type="button"
                onClick={() => {
                  filterOrders(true);
                }}
              >
                Filter
              </button>
            </div>

            <div
              className="col-md-4 col-lg-1 "
              style={{
                verticalAlign: "center",
                marginRight: "-20px",
                marginLeft: "50px",
                marginTop: "5px",
                alignContent: "center",
              }}
              // style={{ margin: "0", padding: "2px" }}
            >
              <span className="mt-2">Sort by:</span>
            </div>
            <div
              className="col-md-8 col-lg-1 mt-0 mt-md-0"
              style={{ margin: "0", padding: "2px", minWidth: "170px" }}
            >
              <select
                className="form-select"
                style={{ margin: "0" }}
                value={selectedSort}
                onChange={(e) => setSelectedSort(e.target.value)}
              >
                <option value="customerName">Customer Name</option>
                <option value="customerPhone">Customer Phone</option>
                <option value="alternatePhone">Alternate Phone</option>
                <option value="startDate">Start Date</option>
                <option value="endDate">End Date</option>
                <option value="price">Total Price</option>
                <option value="isDelivery">Is Delivery</option>
                <option value="isPaid">Is Paid</option>
                <option value="currState">Current State</option>
              </select>
            </div>
            <div
              className="col-md-3 col-lg-1 mt-0 mt-md-0"
              style={{ margin: "0", padding: "2px", minWidth: "135px" }}
            >
              <select
                className="form-select"
                style={{ margin: "0" }}
                value={selectedSortingOrder}
                onChange={(e) => setSelectedSortingOrder(e.target.value)}
              >
                <option value="asc">Ascending</option>
                <option value="desc">Descending</option>
              </select>
            </div>
            <div
              className="col-md-2 col-lg-1 mt-2 mt-md-0"
              style={{ margin: "0", padding: "2px" }}
            >
              <button
                className="btn btn-primary"
                type="button"
                onClick={() => {
                  sortOrders();
                }}
              >
                Sort
              </button>
            </div>
          </div>
        </div>
        <hr className="my-3 custom-line" />
        <div className="row">
          <div className="justify-content-start col-3 d-flex">
            <button
              className="btn btn-primary btn-block ms-0"
              style={{ backgroundColor: "green", borderColor: "green" }}
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
              className="btn btn-primary btn-block ms-1"
              data-bs-toggle="modal"
              data-bs-target="#addCustomerModal"
              style={{ backgroundColor: "green", borderColor: "green" }}
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
                          //style={{ marginRight: "15px" }}
                          style={{
                            marginRight: "15px",
                            backgroundColor: "green",
                            borderColor: "green",
                          }}
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
      />
    </div>
  );
};

export default Orders;
