import { useState } from "react";
import Alert from "react-bootstrap/Alert";
import Button from "react-bootstrap/Button";
import Col from "react-bootstrap/Col";
import Form from "react-bootstrap/Form";
import Row from "react-bootstrap/Row";
import API from "../api";

const Register = () => {
  const [formInput, setFormInput] = useState({
    name: "",
    username: "",
    password: "",
    country: ""
  });
  const [confirmPassword, setConfirmPassword] = useState("");
  const [msg, setMsg] = useState("");
  const [variant, setVariant] = useState("");
  const [show, setShow] = useState(false);

  const handleSubmit = (e) => {
    e.preventDefault();
    if (formInput.password !== confirmPassword) {
      setMsg("Passwords don't match");
      setShow(true);
      setVariant("danger");
      return;
    }
    const body = JSON.stringify(formInput);

    API.post("/api/auth/register", body)
      .then((res) => {
        setMsg("Registration Successful. Login Now");
        setVariant("success");
        setShow(true);
      })
      .catch((err) => {
        setMsg("Registration Failed. Please try again");
        setVariant("danger");
        setShow(true);
      });
  };

  const handleChange = (e) => {
    e.preventDefault();
    setFormInput((prevState) => ({
      ...prevState,
      [e.target.name]: e.target.value
    }));
  };

  const closeAlert = (e) => {
    setShow(false);
    setMsg("");
  };

  return (
    <Form onSubmit={handleSubmit}>
      <Form.Group as={Row} className="mb-2">
        <Form.Label column sm="3">
          Name:
        </Form.Label>
        <Col sm="9" className="mt-1">
          <Form.Control
            name="name"
            type="text"
            placeholder="Enter Name"
            value={formInput.name}
            onChange={handleChange}
          />
        </Col>
      </Form.Group>
      <Form.Group as={Row} className="mb-2">
        <Form.Label column sm="3">
          Username
        </Form.Label>
        <Col sm="9" className="mt-1">
          <Form.Control
            name="username"
            type="text"
            placeholder="Enter Username"
            value={formInput.username}
            onChange={handleChange}
          />
        </Col>
      </Form.Group>
      <Form.Group as={Row} className="mb-3">
        <Form.Label column sm="3">
          Password
        </Form.Label>
        <Col sm="9" className="mt-1">
          <Form.Control
            name="password"
            type="password"
            placeholder="Enter Password"
            value={formInput.password}
            onChange={handleChange}
          />
        </Col>
      </Form.Group>
      <Form.Group as={Row} className="mb-3">
        <Form.Label column sm="3">
          Confirm Password
        </Form.Label>
        <Col sm="9" className="mt-1">
          <Form.Control
            name="confirmPassword"
            type="password"
            placeholder="Confirm Password"
            value={confirmPassword}
            onChange={(e) => setConfirmPassword(e.target.value)}
          />
        </Col>
      </Form.Group>
      <Form.Group as={Row} className="mb-3">
        <Form.Label column sm="3">
          Enter Country
        </Form.Label>
        <Col sm="9" className="mt-1">
          <Form.Control
            name="country"
            type="text"
            placeholder="Enter Country"
            value={formInput.country}
            onChange={handleChange}
          />
        </Col>
      </Form.Group>
      <Alert show={show} variant={variant} onClose={closeAlert} dismissible>
        {msg}
      </Alert>
      <Button variant="primary" type="submit">
        Register
      </Button>
    </Form>
  );
};

export default Register;
