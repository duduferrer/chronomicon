import icon from "../../assets/icon-vector.svg";
import Button from "../../components/Button";
import Input from "../../components/Input";
const LoginCard = () => {
  return (
    <>
      <div className="w-[435px] h-[435px] bg-white rounded-xl shadow-2xl px-12">
        <div className="pt-20">
          <div className="space-x-4 flex justify-center">
            <img src={icon} className="w-14 drop-shadow-xl" />
            <p className="font-normal text-5xl font-archivo-black text-sky-700 drop-shadow-lg">
              MW012
            </p>
          </div>
        </div>
        <div className="mt-6  flex flex-col gap-y-6">
          <Input htmlFor="user" id="user" placeholder="Usuário" type="text" />
          <Input
            htmlFor="password"
            id="pass"
            placeholder="Senha"
            type="password"
          />
        </div>
        <div className="flex pt-1 justify-between text-gray-400">
          <label className="flex hover: cursor-pointer">
            <input type="checkbox" className="" />{" "}
            <p className="italic text-xs pl-1 pt-0.5">Lembrar de mim</p>
          </label>
          <div>
            <p className="italic text-sm hover:cursor-pointer hover:underline">
              Esqueceu a senha?
            </p>
          </div>
        </div>
        <div className="pt-10 flex justify-center">
          <Button text="Entrar" w="w-64" />
        </div>
      </div>
    </>
  );
};

export default LoginCard;
