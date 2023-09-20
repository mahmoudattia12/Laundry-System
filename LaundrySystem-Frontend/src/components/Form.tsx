import React, { ReactNode } from "react";
import { Link } from "react-router-dom";

export interface FormFieldProps {
  label: string;
  type: string;
  name: string;
  value: string;
  onChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
  error?: string;
  passButton?: ReactNode;
  notRequired?: boolean;
}

interface FormProps {
  fields: FormFieldProps[];
  onSubmit: (e: React.FormEvent) => void;
  buttonText: string;
  beforeLink?: string;
  insideLink?: string;
  path: string;
  beforeLink2?: string;
  insideLink2?: string;
  path2: string;
}

const Form = ({
  fields,
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
              placeholder={"Enter " + field.name}
              required={!field.notRequired}
            />
            {field.passButton}
          </div>
          {field.error && <div className="error-message">{field.error}</div>}
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
    </form>
  );
};

export default Form;
