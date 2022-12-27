import { useEffect } from "react";
import API from "../api";
import { useAuth } from "../context/AuthProvider";

const Home = () => {
  const { user, setUser } = useAuth();

  useEffect(() => {
    API.get("/api/user/details")
      .then((resp) => {
        const { data } = resp;
        const payload = { ...user, ...data };
        setUser(payload);
      })
      .catch((err) => {
        console.error(err);
      });
  }, []);

  return (
    <div>
      Welcome {user.name}, Username: {user.username}, Country: {user.country}
    </div>
  );
};

export default Home;
