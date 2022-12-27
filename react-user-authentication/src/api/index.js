import axios from "axios";

const baseURL = "http://localhost:8080/";

const API = axios.create({
  baseURL,
  headers: { "Content-type": "application/json" },
  withCredentials: true
});

export default API;
