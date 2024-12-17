import { Product } from "../interfaces/product.interface";

export const initProducts: Product[] = [
  {
    id: Date.now().toString(),
    name: "Product 1",
    price: 100,
    description: "Description 1",
  },
  {
    id: Date.now().toString() + 1,
    name: "Product 2",
    price: 200,
    description: "Description 2",
  },
  {
    id: Date.now().toString() + 2,
    name: "Product 3",
    price: 300,
    description: "Description 3",
  },
];
