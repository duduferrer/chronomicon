const Button = ({ text, w, h }) => {
  return (
    <a
      className={`inline-block rounded-sm bg-indigo-600 px-8 py-3 text-xl font-bold text-white transition hover:scale-110 text-center hover:shadow-xl focus:ring-3 focus:outline-hidden ${w} ${h}`}
      href="#"
    >
      {text}
    </a>
  );
};

export default Button;
