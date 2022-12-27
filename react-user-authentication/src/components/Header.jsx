import Button from "react-bootstrap/Button";
import Container from "react-bootstrap/Container";
import Nav from "react-bootstrap/Nav";
import Navbar from "react-bootstrap/Navbar";
import { Link } from "react-router-dom";
import API from "../api";
import { defaultUser, useAuth } from "../context/AuthProvider";

const Header = () => {
  const { user, setUser } = useAuth();

  const handleLogout = (e) => {
    e.preventDefault();
    API.post("/api/auth/logout").then((res) => {
      setUser(defaultUser);
    });
  };

  return (
    <Navbar bg="dark" variant="dark" expand="lg">
      <Container>
        <Navbar.Brand as={Link} to="/">
          User App
        </Navbar.Brand>
        <Navbar.Toggle aria-controls="basic-navbar-nav" />
        <Navbar.Collapse id="basic-navbar-nav">
          <Nav className="me-auto">
            {!user.auth && (
              <Nav.Link as={Link} to="/login">
                Login
              </Nav.Link>
            )}
            {!user.auth && (
              <Nav.Link as={Link} to="/register">
                Register
              </Nav.Link>
            )}
            {user.auth && (
              <Nav.Link as={Link} to="/">
                Home
              </Nav.Link>
            )}
            {user.auth && (
              <Nav.Link as={Link} to="/home">
                Details
              </Nav.Link>
            )}
          </Nav>
          {user.auth && <Button onClick={handleLogout}>Logout</Button>}
        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
};

export default Header;
