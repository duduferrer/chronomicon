const Input = ({ type, id, placeholder, htmlFor }) => {
  return (
    <label htmlFor={htmlFor} class="relative">
      <input
        type={type}
        id={id}
        placeholder=""
        class="peer mt-0.5 w-full rounded border-gray-500 shadow-sm sm:text-sm h-11 bg-gray-100 px-5"
      />

      <span class="absolute left-3 top-1/2 -translate-y-1/2 bg-gray-100 px-1 text-sm font-medium text-gray-700 transition-all duration-200 peer-placeholder-shown:top-1/3 peer-placeholder-shown:translate-y-0 peer-focus:top-0 peer-focus:-translate-y-full peer-placeholder-shown:bg-transparent">
        {placeholder}
      </span>
    </label>
  );
};

export default Input;
