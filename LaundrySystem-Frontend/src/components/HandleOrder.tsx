import React, { useState, useEffect } from "react";
import { Modal, Form, Button, InputGroup, FormControl } from "react-bootstrap";
import axios from "axios";
import { Order } from "../pages/home/Orders";

export interface OrderItem {
  type: string;
  customType?: string;
  serviceCategory: string;
  customService?: string;
  price: number;
  discount: number;
  quantity: number;
}

interface HandleOrderProps {
  show: boolean;
  onHide: () => void;
  laundryName: string;
  isUpdate: boolean;
  oldOrder?: Order | null;
  headerTitle: string;
  buttonName: string;
}

const HandleOrder = ({
  show,
  onHide,
  laundryName,
  isUpdate,
  oldOrder,
  headerTitle,
  buttonName,
}: HandleOrderProps) => {
  const [orderItems, setOrderItems] = useState<OrderItem[]>([
    {
      type: "suit",
      serviceCategory: "dryCleaning",
      price: 0,
      discount: 0,
      quantity: 0,
    },
  ]);

  // Initialize the orderNotes state
  const [orderNotes, setOrderNotes] = useState<string[]>([]);

  // Initialize the formData state
  const [formData, setFormData] = useState({
    totalPrice: 0,
    customerPhone: "",
    alternatePhone: "",
    isDelivery: "false",
    startDateTime: "",
    endDateTime: "",
    currState: "new",
    isPaid: "false",
  });

  // Use useEffect to update state based on isUpdate and oldOrder
  useEffect(() => {
    if (isUpdate && oldOrder) {
      setOrderItems(oldOrder.items || []);
      setOrderNotes(oldOrder.notes || []);
      setFormData({
        totalPrice: oldOrder.totalPrice || 0,
        customerPhone: oldOrder.customerPhone || "",
        alternatePhone: oldOrder.alternatePhone || "",
        isDelivery: oldOrder.isDelivery.toString() || "false",
        startDateTime: oldOrder.startDateTime || "",
        endDateTime: oldOrder.dueDateTime || "",
        currState: oldOrder.currState || "new",
        isPaid: oldOrder.isPaid.toString() || "false",
      });
    }
  }, [isUpdate, oldOrder]);

  const [formErrors, setFormErrors] = useState<
    Record<string, string | boolean>
  >({});

  const addNewItem = () => {
    setOrderItems([
      ...orderItems,
      {
        type: "suit",
        serviceCategory: "dryCleaning",
        price: 0,
        discount: 0,
        quantity: 0,
      },
    ]);
  };

  const deleteItem = (index: number) => {
    if (orderItems.length > 1) {
      const updatedItems = [...orderItems];
      updatedItems.splice(index, 1);
      setOrderItems(updatedItems);
    }
  };

  const addNewNote = () => {
    setOrderNotes([...orderNotes, ""]);
  };

  const deleteNote = (index: number) => {
    if (orderNotes.length > 0) {
      const updatedNotes = [...orderNotes];
      updatedNotes.splice(index, 1);
      setOrderNotes(updatedNotes);
    }
  };

  const handleChange = <K extends keyof OrderItem>(
    index: number,
    field: K,
    value: OrderItem[K]
  ) => {
    const updatedItems = [...orderItems];
    updatedItems[index][field] = value;

    if (field === "type" && value !== "other") {
      updatedItems[index].customType = "";
    }
    if (field === "serviceCategory" && value !== "other") {
      updatedItems[index].customService = "";
    }
    setOrderItems(updatedItems);
  };

  const handleNotesChange = (index: number, value: string) => {
    const updatedNotes = [...orderNotes];
    updatedNotes[index] = value;
    setOrderNotes(updatedNotes);
  };

  const handleFormChange = (field: keyof typeof formData, value: any) => {
    setFormData({
      ...formData,
      [field]: value,
    });
  };

  const validateForm = () => {
    const errors: Record<string, string | boolean> = {};

    orderItems.forEach((item, index) => {
      if (item.type === "other" && !item.customType) {
        errors[`item.customType${index}`] = "Custom Type is required";
      } else if (item.type === "other" && item.customType) {
        item.type = item.customType;
      }

      if (item.serviceCategory === "other" && !item.customService) {
        errors[`item.customService${index}`] = "Custom Service is required";
      } else if (item.serviceCategory === "other" && item.customService) {
        item.serviceCategory = item.customService;
      }
      if (!item.price || item.price <= 0) {
        errors[`item.piecePrice${index}`] = "Piece Price must be positive";
      }
      if (!item.quantity || item.quantity <= 0) {
        errors[`item.quantity${index}`] = "Quantity must be positive";
      }
      if (item.discount < 0) {
        errors[`item.discount${index}`] = "discount can't be negative";
      }
    });

    orderNotes.forEach((note, index) => {
      if (!note) {
        errors[`note${index}`] = "Note is required";
      }
    });

    const phonePattern = /^(0\d{10}|0\d{8})$/;
    if (!phonePattern.test(formData.customerPhone)) {
      errors.customerPhone =
        "Customer Phone must be exactly 9 or 11 digits and start with 0";
    }
    if (
      formData.alternatePhone &&
      !phonePattern.test(formData.alternatePhone)
    ) {
      errors.alternatePhone =
        "Alternate Phone must be exactly 9 or 11 digits and start with 0";
    }

    if (!formData.startDateTime || isNaN(Date.parse(formData.startDateTime))) {
      errors.startDateTime = "Start Date Time is required";
    }

    if (!formData.endDateTime || isNaN(Date.parse(formData.endDateTime))) {
      errors.endDateTime = "End Date Time is required";
    }
    console.log(errors.startDateTime, " & ", errors.endDateTime);
    setFormErrors(errors);

    return Object.keys(errors).length === 0;
  };

  function isNumeric(str: string): boolean {
    // Use the "+" operator to attempt conversion
    const numericValue = +str;

    // Check if the conversion result is a finite number
    return !isNaN(numericValue) && isFinite(numericValue);
  }
  const resetWindow = () => {
    setFormData({
      totalPrice: 0,
      customerPhone: "",
      alternatePhone: "",
      isDelivery: "false",
      startDateTime: "",
      endDateTime: "",
      currState: "new",
      isPaid: "false",
    });
    setOrderItems([
      {
        type: "suit",
        serviceCategory: "dryCleaning",
        price: 0,
        discount: 0,
        quantity: 0,
      },
    ]);
    setOrderNotes([]);
    setFormErrors({});
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (validateForm()) {
      var toSent: any = {};
      toSent = formData;
      toSent.laundryName = laundryName;
      console.log(toSent);
      console.log(orderNotes);
      console.log(orderItems);
      if (!isUpdate) {
        try {
          const response = await axios.post(
            `http://localhost:9080/order/add`,
            toSent
          );
          console.log(response.data);
          if (isNumeric(response.data)) {
            const orderID: number = Number(response.data);
            console.log("orderID ", orderID);
            //order added let's add its notes & items

            var responseData: string = "";
            if (orderNotes.length > 0) {
              console.log(orderNotes);
              const notesResponse = await axios.post(
                `http://localhost:9080/order/addNotes?orderID=${orderID}`,
                orderNotes
              );
              responseData = notesResponse.data;
            }
            console.log("note response: ", responseData);
            if (responseData === "" || responseData === "SUCCESS") {
              //add items
              console.log("order items  ", orderItems);

              const itemsResponse = await axios.post(
                `http://localhost:9080/order/addItems?orderID=${orderID}`,
                orderItems
              );
              if (itemsResponse.data.includes("SUCCESS")) {
                console.log("the whole order is added");
                //the whole order is added
                alert("SUCCESS");
                resetWindow();
                onHide();
              } else {
                //here delete the added order with its id (handled in the back --> test)
                alert(itemsResponse.data);
              }
            } else {
              //here delete the added order with its id (handled in the back --> test)
              alert(responseData);
            }
          } else {
            alert(response.data);
          }
        } catch (error) {
          console.error("Error:", error);
          alert(error);
        }
      } else {
        console.log("update handle clicked");
        oldOrder?.ID !== undefined ? (toSent.ID = oldOrder.ID) : "";
        toSent.items = orderItems;
        toSent.notes = orderNotes;

        console.log("update to sent: ", toSent);

        const orderID = toSent.ID;
        console.log("orderID", toSent.ID);
        if (toSent.ID) {
          try {
            const response = await axios.put(
              `http://localhost:9080/order/update/${toSent.ID}`,
              toSent
            );
            console.log("update response : -- ", response.data);

            alert(response.data);
            if (response.data === "SUCCESS") {
              resetWindow();
              onHide();
            }
          } catch (error) {
            console.error("Error:", error);
            alert(error);
          }
        }
      }
    }
  };

  return (
    <Modal show={show} onHide={onHide} size="lg" dialogClassName="custom-modal">
      <Modal.Header closeButton onClick={resetWindow}>
        <Modal.Title>{headerTitle}</Modal.Title>
      </Modal.Header>

      <Modal.Body>
        <Form onSubmit={handleSubmit}>
          <div className="mb-3">
            {/* Order Items */}
            {orderItems.map((item, index) => (
              <div key={index} className="mb-3 row">
                <div className="col-md-3">
                  <Form.Group>
                    <Form.Label>Type</Form.Label>
                    <InputGroup>
                      <Form.Control
                        as="select"
                        value={item.type}
                        onChange={(e) =>
                          handleChange(index, "type", e.target.value)
                        }
                      >
                        <option value="suit">Suit</option>
                        <option value="shirt">Shirt</option>
                        <option value="t-shirt">T-Shirt</option>
                        <option value="sweater">Sweater</option>
                        <option value="jacket">Jacket</option>
                        <option value="coat">Coat</option>
                        <option value="jeans">Jeans</option>
                        <option value="pajamas">Pajamas</option>
                        <option value="carpet">Carpet</option>
                        <option value="other">Other</option>
                      </Form.Control>
                      {item.type === "other" && (
                        <FormControl
                          type="text"
                          placeholder="Enter the type"
                          value={item.customType || ""}
                          onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
                            handleChange(index, "customType", e.target.value)
                          }
                          isInvalid={!!formErrors[`item.customType${index}`]}
                        />
                      )}
                      <Form.Control.Feedback type="invalid">
                        {formErrors[`item.customType${index}`]}
                      </Form.Control.Feedback>
                    </InputGroup>
                  </Form.Group>
                </div>

                <div className="col-md-3">
                  <Form.Group>
                    <Form.Label>Service Category</Form.Label>
                    <InputGroup>
                      <Form.Control
                        as="select"
                        value={item.serviceCategory}
                        onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
                          handleChange(index, "serviceCategory", e.target.value)
                        }
                      >
                        <option value="dryCleaning">Dry Cleaning</option>
                        <option value="normalWashing">Normal Washing</option>
                        <option value="ironing">Ironing</option>
                        <option value="other">Other</option>
                      </Form.Control>
                      {item.serviceCategory === "other" && (
                        <FormControl
                          type="text"
                          placeholder="Enter the service"
                          value={item.customService || ""}
                          onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
                            handleChange(index, "customService", e.target.value)
                          }
                          isInvalid={!!formErrors[`item.customService${index}`]}
                        />
                      )}
                      <Form.Control.Feedback type="invalid">
                        {formErrors[`item.customService${index}`]}
                      </Form.Control.Feedback>
                    </InputGroup>
                  </Form.Group>
                </div>
                <div className="col-md-2">
                  <Form.Group>
                    <Form.Label>Piece Price</Form.Label>
                    <Form.Control
                      type="number"
                      placeholder="Piece Price"
                      value={item.price}
                      onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
                        handleChange(index, "price", parseFloat(e.target.value))
                      }
                      isInvalid={!!formErrors[`item.piecePrice${index}`]}
                    />
                    <Form.Control.Feedback type="invalid">
                      {formErrors[`item.piecePrice${index}`]}
                    </Form.Control.Feedback>
                  </Form.Group>
                </div>
                <div className="col-md-2">
                  <Form.Group>
                    <Form.Label>Discount</Form.Label>
                    <Form.Control
                      type="number"
                      placeholder="Discount"
                      value={item.discount}
                      onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
                        handleChange(
                          index,
                          "discount",
                          parseFloat(e.target.value)
                        )
                      }
                      isInvalid={!!formErrors[`item.discount${index}`]}
                    />
                    <Form.Control.Feedback type="invalid">
                      {formErrors[`item.discount${index}`]}
                    </Form.Control.Feedback>
                  </Form.Group>
                </div>
                <div className="col-md-2">
                  <Form.Group>
                    <Form.Label>Quantity</Form.Label>
                    <Form.Control
                      type="number"
                      placeholder="Quantity"
                      value={item.quantity}
                      onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
                        handleChange(
                          index,
                          "quantity",
                          parseInt(e.target.value)
                        )
                      }
                      isInvalid={!!formErrors[`item.quantity${index}`]}
                    />
                    <Form.Control.Feedback type="invalid">
                      {formErrors[`item.quantity${index}`]}
                    </Form.Control.Feedback>
                  </Form.Group>
                </div>
                <div>
                  {index > 0 && (
                    <Button
                      className="mt-2"
                      variant="danger"
                      onClick={() => deleteItem(index)}
                    >
                      Delete Item
                    </Button>
                  )}
                </div>
              </div>
            ))}
            <Button variant="primary" onClick={addNewItem}>
              Add Item
            </Button>
            <hr className="my-3 custom-line" />
          </div>

          <div className="mb-3">
            {/* Order Notes */}
            {orderNotes.map((note, index) => (
              <div key={index} className="mb-3">
                <Form.Group>
                  <div className="row mb-3">
                    <div className="col-md-10">
                      <Form.Label>Note {index + 1}</Form.Label>
                      <Form.Control
                        as="textarea"
                        placeholder="Enter the note"
                        rows={1}
                        value={note}
                        onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
                          handleNotesChange(index, e.target.value)
                        }
                        isInvalid={!!formErrors[`note${index}`]}
                      />
                      <Form.Control.Feedback type="invalid">
                        {formErrors[`note${index}`]}
                      </Form.Control.Feedback>
                    </div>
                    <div className="col">
                      <Button
                        style={{ marginTop: "33px" }}
                        variant="danger"
                        onClick={() => deleteNote(index)}
                      >
                        Delete Note
                      </Button>
                    </div>
                  </div>
                </Form.Group>
              </div>
            ))}
            <Button variant="primary" onClick={addNewNote}>
              Add Note
            </Button>
            <hr className="my-3 custom-line custom-line-notes" />
          </div>

          <Form.Group>
            <div className="row mb-3">
              {!isUpdate && (
                <div className="col-md-3">
                  {/* Customer Phone */}
                  <Form.Label>Customer Phone</Form.Label>
                  <Form.Control
                    type="tel"
                    placeholder="Customer Phone"
                    value={formData.customerPhone}
                    onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
                      handleFormChange("customerPhone", e.target.value)
                    }
                    isInvalid={!!formErrors[`customerPhone`]}
                  />
                  <Form.Control.Feedback type="invalid">
                    {formErrors[`customerPhone`]}
                  </Form.Control.Feedback>
                </div>
              )}
              <div className="col-md-3">
                {/* Alternate Phone */}
                <Form.Label>Alternate Phone</Form.Label>
                <Form.Control
                  type="tel"
                  placeholder="Alternate Phone"
                  value={formData.alternatePhone}
                  onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
                    handleFormChange("alternatePhone", e.target.value)
                  }
                  isInvalid={!!formErrors[`alternatePhone`]}
                />
                <Form.Control.Feedback type="invalid">
                  {formErrors[`alternatePhone`]}
                </Form.Control.Feedback>
              </div>
              <div className="col-md-3">
                {/* Start Date Time */}
                <Form.Label>Start Date Time</Form.Label>
                <Form.Control
                  type="datetime-local"
                  placeholder="Start Date Time"
                  value={formData.startDateTime}
                  onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
                    handleFormChange("startDateTime", e.target.value)
                  }
                  isInvalid={!!formErrors[`startDateTime`]}
                />
                <Form.Control.Feedback type="invalid">
                  {formErrors[`startDateTime`]}
                </Form.Control.Feedback>
              </div>
              <div className="col-md-3">
                {/* End Date Time */}
                <Form.Label>End Date Time</Form.Label>
                <Form.Control
                  type="datetime-local"
                  placeholder="End Date Time"
                  value={formData.endDateTime}
                  onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
                    handleFormChange("endDateTime", e.target.value)
                  }
                  isInvalid={!!formErrors[`endDateTime`]}
                />
                <Form.Control.Feedback type="invalid">
                  {formErrors[`endDateTime`]}
                </Form.Control.Feedback>
              </div>
            </div>
          </Form.Group>

          <Form.Group>
            <div className="row mb-3">
              <div className="col-md-4">
                {/* Is Paid */}
                <Form.Label>Is Paid</Form.Label>
                <Form.Control
                  as="select"
                  value={formData.isPaid}
                  onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
                    handleFormChange("isPaid", e.target.value)
                  }
                >
                  <option value="true">Yes</option>
                  <option value="false">No</option>
                </Form.Control>
              </div>
              <div className="col-md-4">
                {/* Is Delivery */}
                <Form.Label>Is Delivery</Form.Label>
                <Form.Control
                  as="select"
                  value={formData.isDelivery}
                  onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
                    handleFormChange("isDelivery", e.target.value)
                  }
                >
                  <option value="true">Yes</option>
                  <option value="false">No</option>
                </Form.Control>
              </div>
              <div className="col-md-4">
                {/* Current State */}
                <Form.Label>Current State</Form.Label>
                <Form.Control
                  as="select"
                  value={formData.currState}
                  onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
                    handleFormChange("currState", e.target.value)
                  }
                >
                  <option value="new">New</option>
                  <option value="inprogress">In Progress</option>
                  <option value="finished">Finished</option>
                  <option value="delivered">Delivered</option>
                </Form.Control>
              </div>
            </div>
          </Form.Group>

          <Button variant="primary" type="submit">
            {buttonName}
          </Button>
        </Form>
      </Modal.Body>
    </Modal>
  );
};

export default HandleOrder;
