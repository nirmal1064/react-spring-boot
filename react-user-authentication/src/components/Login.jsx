import React, { useState } from "react";
import Alert from "react-bootstrap/Alert";
import Button from "react-bootstrap/Button";
import Col from "react-bootstrap/Col";
import Form from "react-bootstrap/Form";
import Row from "react-bootstrap/Row";
import { useNavigate } from "react-router-dom";
import API from "../api";
import { useAuth } from "../context/AuthProvider";

const Login = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [errorMsg, setErrorMsg] = useState("");
  const [show, setShow] = useState(false);
  const { user, setUser } = useAuth();
  const navigate = useNavigate();

  const closeAlert = (e) => {
    setShow(false);
    setErrorMsg("");
  };

  const handleLogin = (e) => {
    e.preventDefault();

    const body = JSON.stringify({ username, password });

    API.post("/api/auth/login", body)
      .then((res) => {
        const payload = { ...user, auth: true };
        setUser(payload);
        navigate("/");
      })
      .catch((err) => {
        console.error(err.message);
        setErrorMsg("Login Failed");
        setShow(true);
      });
  };

  return (
    <Form onSubmit={handleLogin}>
      <Form.Group as={Row} className="mb-2">
        <Form.Label column sm="2">
          Username
        </Form.Label>
        <Col sm="10" className="mt-1">
          <Form.Control
            name="username"
            type="text"
            placeholder="Enter Username"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
          />
        </Col>
      </Form.Group>
      <Form.Group as={Row} className="mb-3">
        <Form.Label column sm="2">
          Password
        </Form.Label>
        <Col sm="10" className="mt-1">
          <Form.Control
            name="password"
            type="password"
            placeholder="Enter Password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
        </Col>
      </Form.Group>
      <Alert show={show} variant="danger" onClose={closeAlert} dismissible>
        {errorMsg}
      </Alert>
      <Button variant="primary" type="submit">
        Login
      </Button>
    </Form>
  );
};

export default Login;
