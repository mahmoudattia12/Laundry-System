import React from "react";
import { Modal } from "react-bootstrap";
import { Order } from "../pages/home/Orders"; // Import the Order and OrderItem types
import { OrderItem } from "./HandleOrder";

interface OrderDetailsModalProps {
  show: boolean;
  order: Order | null;
  onHide: () => void;
}

const OrderDetailsModal: React.FC<OrderDetailsModalProps> = ({
  show,
  order,
  onHide,
}) => {
  return (
    <Modal show={show} onHide={onHide} size="lg">
      <Modal.Header closeButton>
        <Modal.Title>Order Details</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        {order && (
          <div>
            <p className="fs-5" style={{ color: "#2502b5" }}>
              <span style={{ fontWeight: "bold", color: "black" }}>
                Customer Name:
              </span>{" "}
              {order.customerName}
            </p>
            {order.customerAddress && (
              <p className="fs-5" style={{ color: "#2502b5" }}>
                <span style={{ fontWeight: "bold", color: "black" }}>
                  Customer Address:
                </span>{" "}
                {order.customerAddress}
              </p>
            )}

            <div className="row">
              <div className="col">
                <p className="fs-5" style={{ color: "#2502b5" }}>
                  <span style={{ fontWeight: "bold", color: "black" }}>
                    Customer Phone:
                  </span>{" "}
                  {order.customerPhone}
                </p>
              </div>
              <div className="col">
                {order.alternatePhone && (
                  <p className="fs-5" style={{ color: "#2502b5" }}>
                    <span style={{ fontWeight: "bold", color: "black" }}>
                      Alternate Phone:
                    </span>{" "}
                    {order.alternatePhone}
                  </p>
                )}
              </div>
            </div>
            <div className="row">
              <div className="col">
                <p className="fs-5" style={{ color: "#2502b5" }}>
                  <span style={{ fontWeight: "bold", color: "black" }}>
                    Start Date & Time:
                  </span>{" "}
                  {order.startDateTime}
                </p>
              </div>
              <div className="col">
                <p className="fs-5" style={{ color: "#2502b5" }}>
                  <span style={{ fontWeight: "bold", color: "black" }}>
                    Due Date & Time:
                  </span>{" "}
                  {order.dueDateTime}
                </p>
              </div>
            </div>
            <div className="row">
              <div className="col">
                <p className="fs-5" style={{ color: "#2502b5" }}>
                  <span style={{ fontWeight: "bold", color: "black" }}>
                    Toltal Price:
                  </span>{" "}
                  {order.totalPrice}
                </p>
              </div>
              <div className="col">
                <p className="fs-5" style={{ color: "#2502b5" }}>
                  <span style={{ fontWeight: "bold", color: "black" }}>
                    Is Paid:
                  </span>{" "}
                  {order.isPaid ? "Yes" : "No"}
                </p>
              </div>
              <div className="col">
                <p className="fs-5" style={{ color: "#2502b5" }}>
                  <span style={{ fontWeight: "bold", color: "black" }}>
                    Is Delivery:
                  </span>{" "}
                  {order.isDelivery ? "Yes" : "No"}
                </p>
              </div>
              <div className="col">
                <p className="fs-5" style={{ color: "#2502b5" }}>
                  <span style={{ fontWeight: "bold", color: "black" }}>
                    State:
                  </span>{" "}
                  {order.currState}
                </p>
              </div>
            </div>
            <hr className="my-3" />
            <h5 style={{ fontWeight: "bold" }}>Order Items:</h5>
            <table className="table centered-table">
              <thead>
                <tr>
                  <th>Type</th>
                  <th>Service Category</th>
                  <th>Price</th>
                  <th>Discount</th>
                  <th>Quantity</th>
                </tr>
              </thead>
              <tbody>
                {order.items.map((item, index) => (
                  <tr key={index}>
                    <td>{item.type}</td>
                    <td>{item.serviceCategory}</td>
                    <td>{item.price}</td>
                    <td>{item.discount}</td>
                    <td>{item.quantity}</td>
                  </tr>
                ))}
              </tbody>
            </table>
            {order.notes.length > 0 && (
              <>
                <h5 style={{ fontWeight: "bold" }}>Notes:</h5>
                <ul>
                  {order.notes.map((note, index) => (
                    <li key={index}>{note}</li>
                  ))}
                </ul>
              </>
            )}
          </div>
        )}
      </Modal.Body>
      <Modal.Footer className="justify-content-center">
        <button className="btn btn-secondary" onClick={onHide}>
          Close
        </button>
      </Modal.Footer>
    </Modal>
  );
};

export default OrderDetailsModal;
