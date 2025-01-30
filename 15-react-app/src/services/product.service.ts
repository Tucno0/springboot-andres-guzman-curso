import {Product} from "../interfaces/product.interface";
import axios from "axios";

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

const baseUrl = "http://localhost:8080/products";

export const findAll = async () => {
  try {
    const response = await axios.get(baseUrl);
    return response.data;
  } catch (error) {
    console.error(error);
    console.log("Error while fetching products");
    return [];
  }
}

export const create = async ({ name, price, description } :Partial<Product>) => {
  console.log(name, price, description);
  try {
    const response = await axios.post(baseUrl, { name, price, description });
    return response.data;
  } catch (error) {
    console.error(error);
    console.log("Error while creating product");
    return undefined;
  }
}

export const update = async (product: Product) => {
  try {
    const response = await axios.put(`${baseUrl}/${product.id}`, {
      name: product.name,
      price: product.price,
      description: product.description,
    });
    return response.data;
  } catch (error) {
    console.error(error);
    console.log("Error while updating product");
    return undefined;
  }
}

export const remove = async (id: string) => {
  try {
    const response = await axios.delete(`${baseUrl}/${id}`);
    return response.data;
  } catch (error) {
    console.error(error);
    console.log("Error while deleting product");
    return undefined;
  }
}