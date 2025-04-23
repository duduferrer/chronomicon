import Header from "../../components/Header";
import LoginCard from "./LoginCard";

const Login = () => {
  return (
    <div className="w-full h-dvh bg-gray-200">
      <Header />
      <div className="mx-auto mt-28 w-fit h-fit">
        <LoginCard />
      </div>
    </div>
  );
};

export default Login;
