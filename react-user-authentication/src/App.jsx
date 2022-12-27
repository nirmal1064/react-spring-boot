import Container from "react-bootstrap/Container";
import { Route, Routes } from "react-router-dom";
import AuthRoute from "./components/AuthRoute";
import Header from "./components/Header";
import Home from "./components/Home";
import Login from "./components/Login";
import Register from "./components/Register";

const App = () => {
  return (
    <Container style={{ width: "800px" }}>
      <Header />
      <Routes>
        <Route element={<AuthRoute />}>
          <Route path="/" element={<Home />} />
          <Route path="/home" element={<Home />} />
        </Route>
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
      </Routes>
    </Container>
  );
};

export default App;
