import { useEffect, useState } from "react";
import { Product } from "../interfaces/product.interface";

interface Props {
  onAddProduct: (product: Product) => void;
  selectedProduct: Product;
}

export const ProductForm = ({ onAddProduct, selectedProduct }: Props) => {
  const [form, setForm] = useState({
    id: "",
    name: "",
    price: 0,
    description: "",
  });

  const { name, price, description } = form;

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();

    if (!name || !price || !description) {
      alert("All fields are required");
      return;
    }

    onAddProduct({
      id: selectedProduct.id || Date.now().toString(),
      name,
      price,
      description,
    });

    setForm({
      id: "",
      name: "",
      price: 0,
      description: "",
    });
  };

  useEffect(() => {
    setForm(selectedProduct);
  }, [selectedProduct]);

  return (
    <form onSubmit={handleSubmit} className="p-4 border rounded shadow-sm">
      <div className="mb-3">
        <label htmlFor="name" className="form-label">
          Name
        </label>
        <input
          type="text"
          id="name"
          name="name"
          value={name}
          placeholder="Product name"
          onChange={(e) => setForm({ ...form, name: e.target.value })}
          className="form-control"
        />
      </div>

      <div className="mb-3">
        <label htmlFor="price" className="form-label">
          Price
        </label>
        <input
          type="number"
          id="price"
          name="price"
          value={`${price}`}
          placeholder="Product price"
          onChange={(e) =>
            setForm({ ...form, price: parseFloat(e.target.value) })
          }
          className="form-control"
        />
      </div>

      <div className="mb-3">
        <label htmlFor="description" className="form-label">
          Description
        </label>
        <input
          type="text"
          id="description"
          name="description"
          value={description}
          placeholder="Product description"
          onChange={(e) => setForm({ ...form, description: e.target.value })}
          className="form-control"
        />
      </div>

      <button type="submit" className="btn btn-success">
        Submit
      </button>
    </form>
  );
};
