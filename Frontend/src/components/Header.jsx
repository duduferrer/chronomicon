import logo from "../assets/paz-plena.svg";

const Header = () => {
  return (
    <div class="w-full">
      <div class="bg-gray-300 w-full h-full opacity-100">
        <div class="w-full h-52 overflow-hidden opacity-30 relative">
          <div class="w-full h-4 md:h-8 overflow-hidden bg-gray-300 absolute z-30 opacity-80" />
          <img class="object-cover w-full h-max object-top" src={logo} />
          <div class="absolute z-30 bg-gray-300 w-full h-4 md:h-8 bottom-0 opacity-30" />
        </div>
      </div>
      <div class=" bg-gray-300 w-full md:h-8 h-4 opacity-50 top-58" />
    </div>
  );
};

export default Header;
