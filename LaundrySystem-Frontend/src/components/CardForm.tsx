import React, { ReactNode } from "react";
import { Link } from "react-router-dom";
import "./components.css";
import Form, { FormFieldProps } from "./Form";

interface FormProps {
  fields: FormFieldProps[];
  title: string;
  onSubmit: (e: React.FormEvent) => void;
  buttonText: string;
  beforeLink?: string;
  insideLink?: string;
  path: string;
  beforeLink2?: string;
  insideLink2?: string;
  path2: string;
}

const CardForm = ({
  fields,
  title,
  onSubmit,
  buttonText,
  beforeLink,
  insideLink,
  path,
  beforeLink2,
  insideLink2,
  path2,
}: FormProps) => {
  return (
    <div className="container-fluid h-100 d-flex justify-content-center align-items-center white-blue-bg">
      <div className="col-lg-4 col-md-6 col-sm-8">
        <div
          className="card"
          style={{
            background: "linear-gradient(to top, #a3bded 0%, #6991c7 100%)",
          }}
        >
          <div className="card-body">
            <h2
              className="card-title"
              style={{ textAlign: "center", fontWeight: "bold" }}
            >
              {title}
            </h2>

            <Form
              fields={fields}
              onSubmit={onSubmit}
              buttonText={buttonText}
              beforeLink={beforeLink}
              insideLink={insideLink}
              path={path}
              beforeLink2={beforeLink2}
              insideLink2={insideLink2}
              path2={path2}
            />
            {/* <form onSubmit={onSubmit}>
              {fields.map((field, index) => (
                <div className="mb-3" key={index}>
                  <label htmlFor={field.name} className="form-label">
                    {field.label}
                  </label>
                  <div className="input-group">
                    <input
                      type={field.type}
                      className="form-control"
                      id={field.name}
                      name={field.name}
                      value={field.value}
                      onChange={field.onChange}
                      placeholder={"Enter " + field.name}
                      required
                    />
                    {field.passButton}
                  </div>
                  {field.error && (
                    <div className="error-message">{field.error}</div>
                  )}
                </div>
              ))}
              <button type="submit" className="btn btn-primary">
                {buttonText}
              </button>

              <div style={{ textAlign: "center", fontWeight: "bold" }}>
                {beforeLink}
                <Link to={path}>{insideLink}</Link>
              </div>

              <div style={{ textAlign: "center", fontWeight: "bold" }}>
                {beforeLink2}
                <Link to={path2}>{insideLink2}</Link>
              </div>
            </form> */}
          </div>
        </div>
      </div>
    </div>
  );
};

export default CardForm;
