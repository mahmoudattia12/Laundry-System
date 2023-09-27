import React, { useState } from "react";
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

interface FilterAndSortProps {
  entityName: string;
  laundryName: string;
  initialSelected: string;
  adapter: (responseData: any[]) => void;
}

const FilterAndSort = ({
  entityName,
  laundryName,
  initialSelected,
  adapter,
}: FilterAndSortProps) => {
  const [selectedFilter, setSelectedFilter] = useState(initialSelected);
  const [toMeetFilter, setToMeetFilter] = useState("");
  const [toMeetSearch, setToMeetSearch] = useState("");
  const [selectedSort, setSelectedSort] = useState(initialSelected);
  const [selectedSortingOrder, setSelectedSortingOrder] = useState("asc");

  const filterOrders = async (isFilter: boolean) => {
    try {
      var criteria, toMeet;
      isFilter
        ? ((criteria = selectedFilter), (toMeet = toMeetFilter))
        : ((criteria = "search"), (toMeet = toMeetSearch));

      var sendToMeet = toMeet;
      if (
        criteria === "isDelivery" ||
        criteria === "isPaid" ||
        criteria === "isManager"
      ) {
        if (toMeet.toLocaleLowerCase() === "yes") sendToMeet = "true";
        else if (toMeet.toLocaleLowerCase() === "no") sendToMeet = "false";
      }

      if (criteria === "startShift" || criteria === "endShift") {
        sendToMeet += ":00.000000";
      }

      if (sendToMeet === "") {
        alert("please enter the value you want to search/filter");
      } else {
        const toSendEntity = entityName === "empInfo" ? "emp" : entityName;
        const response = await axios.get(
          `http://localhost:9080/filterEntity/${toSendEntity}/${criteria}/${sendToMeet}/${laundryName}`
        );

        adapter(response.data);
      }
    } catch (error) {
      console.error("Error filtering list:", error);
      alert("Error filtering list");
    }
  };

  const sortOrders = async () => {
    try {
      const isAsc = selectedSortingOrder === "asc" ? true : false;

      const toSendEntity = entityName === "empInfo" ? "emp" : entityName;
      const response = await axios.get(
        `http://localhost:9080/sortEntity/${toSendEntity}/${selectedSort}/${isAsc}/${laundryName}`
      );

      adapter(response.data);
    } catch (error) {
      console.error("Error sorting list:", error);
      alert("Error sorting list");
    }
  };

  return (
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
                filterOrders(false); //->
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
            value={selectedFilter} //->
            onChange={(e) => setSelectedFilter(e.target.value)} //->
          >
            {entityName === "ord" && (
              <>
                <option value="customerName">Customer Name</option>
                <option value="customerPhone">Customer Phone</option>
                <option value="alternatePhone">Alternate Phone</option>
                {/* <option value="startDate">Start Date</option>
                <option value="endDate">End Date</option> better in searching*/}
                <option value="price">Total Price</option>
                <option value="isDelivery">Is Delivery</option>
                <option value="isPaid">Is Paid</option>
                <option value="currState">Current State</option>
              </>
            )}
            {entityName === "cus" && (
              <>
                <option value="name">Name</option>
                <option value="phoneNumber">Phone</option>
                <option value="email">Email</option>
                <option value="address">Address</option>
              </>
            )}
            {entityName === "emp" && (
              <>
                <option value="userName">User Name</option>
                <option value="phoneNumber">Phone Number</option>
                <option value="email">Email</option>
                <option value="startShift">Start Shift Time</option>
                <option value="endShift">End Shift Time</option>
                <option value="salary">Salary</option>
                <option value="isManager">Is Manager</option>
              </>
            )}
            {entityName === "empInfo" && (
              <>
                <option value="userName">User Name</option>
                <option value="phoneNumber">Phone Number</option>
                <option value="email">Email</option>
                <option value="startShift">Start Shift Time</option>
                <option value="endShift">End Shift Time</option>
                <option value="isManager">Is Manager</option>
              </>
            )}
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
                : selectedFilter === "startShift"
                ? "HH:mm"
                : selectedFilter === "endShift"
                ? "HH:mm"
                : selectedFilter === "isDelivery"
                ? "yes/no"
                : selectedFilter === "isPaid" || selectedFilter === "isManager"
                ? "yes/no"
                : selectedFilter === "currState"
                ? "new/inprogress/finished/delivered"
                : "Enter filter value..."
            }
            value={toMeetFilter} //->
            onChange={(e) => setToMeetFilter(e.target.value)} //->
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
              filterOrders(true); //->
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
            value={selectedSort} //->
            onChange={(e) => setSelectedSort(e.target.value)} //->
          >
            {entityName === "ord" && (
              <>
                <option value="customerName">Customer Name</option>
                <option value="customerPhone">Customer Phone</option>
                <option value="alternatePhone">Alternate Phone</option>
                <option value="startDate">Start Date</option>
                <option value="endDate">End Date</option>
                <option value="price">Total Price</option>
                <option value="isDelivery">Is Delivery</option>
                <option value="isPaid">Is Paid</option>
                <option value="currState">Current State</option>
              </>
            )}
            {entityName === "cus" && (
              <>
                <option value="name">Name</option>
                <option value="phoneNumber">Phone</option>
                <option value="email">Email</option>
                <option value="address">Address</option>
              </>
            )}
            {entityName === "emp" && (
              <>
                <option value="userName">User Name</option>
                <option value="phoneNumber">Phone Number</option>
                <option value="email">Email</option>
                <option value="startShift">Start Shift Time</option>
                <option value="endShift">End Shift Time</option>
                <option value="salary">Salary</option>
              </>
            )}
            {entityName === "empInfo" && (
              <>
                <option value="userName">User Name</option>
                <option value="phoneNumber">Phone Number</option>
                <option value="email">Email</option>
                <option value="startShift">Start Shift Time</option>
                <option value="endShift">End Shift Time</option>
              </>
            )}
          </select>
        </div>
        <div
          className="col-md-3 col-lg-1 mt-0 mt-md-0"
          style={{ margin: "0", padding: "2px", minWidth: "135px" }}
        >
          <select
            className="form-select"
            style={{ margin: "0" }}
            value={selectedSortingOrder} //->
            onChange={(e) => setSelectedSortingOrder(e.target.value)} //->
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
              sortOrders(); //->
            }}
          >
            Sort
          </button>
        </div>
      </div>
    </div>
  );
};

export default FilterAndSort;
