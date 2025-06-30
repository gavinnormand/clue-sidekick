/** @type {import('tailwindcss').Config} */
export default {
  content: ["./index.html", "./src/**/*.{vue,js,ts,jsx,tsx}"],
  theme: {
    extend: {
      fontFamily: {
        futura: ['"futura-pt"', "sans-serif"],
        "futura-bold": ['"futura-pt-bold"', "sans-serif"],
      },
      colors: {
        background: "#2d2d2d",
        backgroundHover: "#3a3a3a",
      },
    },
  },
  plugins: [],
};
