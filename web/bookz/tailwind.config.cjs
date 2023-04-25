const plugin = require("tailwindcss/plugin");
const colors = require("tailwindcss/colors");

module.exports = {
  content: [
    "./**/*.html",
    "./*.html",
    "./**/*.js",
    "./*.js",
    "./**/*.svelte",
    "./*.svelte",
  ],
  options: {
    safelist: [],
  },
  theme: {
    colors: {
      ...colors,
    },
    extend: {
      colors: {
        level3blue: {
          DEFAULT: "#0081B5",
          50: "#6ED5FF",
          100: "#59CFFF",
          200: "#30C4FF",
          300: "#08B8FF",
          400: "#009EDE",
          500: "#0081B5",
          600: "#00597D",
          700: "#003145",
          800: "#00090D",
          900: "#000000",
        },
        level3yellow: {
          DEFAULT: "#DCE36E",
          50: "#FFFFFF",
          100: "#FDFDF7",
          200: "#F5F7D5",
          300: "#EDF0B2",
          400: "#E4EA90",
          500: "#DCE36E",
          600: "#D1DA3F",
          700: "#B3BC24",
          800: "#878D1B",
          900: "#5A5E12",
        },
      },
      minHeight: {
        "screen-75": "75vh",
        "screen-50": "50vh",
        "screen-70": "70vh",
      },
      fontSize: {
        55: "55rem",
      },
      opacity: {
        80: ".8",
      },
      zIndex: {
        2: 2,
        3: 3,
      },
      inset: {
        "-100": "-100%",
        "-225-px": "-225px",
        "-160-px": "-160px",
        "-150-px": "-150px",
        "-94-px": "-94px",
        "-50-px": "-50px",
        "-29-px": "-29px",
        "-20-px": "-20px",
        "25-px": "25px",
        "40-px": "40px",
        "95-px": "95px",
        "145-px": "145px",
        "195-px": "195px",
        "210-px": "210px",
        "260-px": "260px",
      },
      height: {
        "95-px": "95px",
        "70-px": "70px",
        "350-px": "350px",
        "500-px": "500px",
        "600-px": "600px",
      },
      maxHeight: {
        "860-px": "860px",
      },
      maxWidth: {
        "100-px": "100px",
        "120-px": "120px",
        "150-px": "150px",
        "180-px": "180px",
        "200-px": "200px",
        "210-px": "210px",
        "580-px": "580px",
      },
      minWidth: {
        "140-px": "140px",
        48: "12rem",
      },
      backgroundSize: {
        full: "100%",
      },
    },
  },
  variants: [
    "responsive",
    "group-hover",
    "focus-within",
    "first",
    "last",
    "odd",
    "even",
    "hover",
    "focus",
    "active",
    "visited",
    "disabled",
  ],
  plugins: [],
};
