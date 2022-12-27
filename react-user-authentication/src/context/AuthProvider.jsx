import { createContext, useContext, useEffect, useState } from "react";
import API from "../api";

export const defaultUser = { auth: false, name: "", username: "", country: "" };

const AuthContext = createContext({
  user: defaultUser,
  setUser: () => null
});

export const useAuth = () => useContext(AuthContext);

const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(defaultUser);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    API.get("/api/user/details")
      .then((resp) => {
        const { data } = resp;
        const payload = { ...data, auth: true };
        setUser(payload);
        setLoading(false);
      })
      .catch((err) => {
        console.error(err);
        setLoading(false);
      });
  }, []);

  return (
    <AuthContext.Provider value={{ user, setUser }}>
      {!loading && children}
    </AuthContext.Provider>
  );
};

export default AuthProvider;
