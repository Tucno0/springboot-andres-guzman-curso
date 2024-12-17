import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import { ProductsApp } from "./ProductsApp";

createRoot(document.getElementById("root")!).render(
  <StrictMode>
    <ProductsApp />
  </StrictMode>
);
