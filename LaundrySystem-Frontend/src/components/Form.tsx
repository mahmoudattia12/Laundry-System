import React, { ReactNode } from "react";

interface FormFieldProps {
  label: string;
  type: string;
  name: string;
  value: string;
  onChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
  error?: string;
  passButton?: ReactNode;
}
interface FormProps {
  fields: FormFieldProps[];
  title: string;
  onSubmit: (e: React.FormEvent) => void;
  buttonText: string;
}

const Form = ({ fields, title, onSubmit, buttonText }: FormProps) => {
  return (
    <div className="container-fluid h-100 d-flex justify-content-center align-items-center white-blue-bg">
      <div className="col-lg-6">
        <div className="card">
          <div className="card-body">
            <h2 className="card-title">{title}</h2>
            <form onSubmit={onSubmit}>
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
            </form>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Form;
