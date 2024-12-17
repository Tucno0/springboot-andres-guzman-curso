import { Product } from "../interfaces/product.interface";

interface Props {
  product: Product;
  onDeleteProduct: (id: string) => void;
  onEditProduct: (product: Product) => void;
}

export const ProductDetail = ({ product, onDeleteProduct, onEditProduct }: Props) => {
  return (
    <tr>
      <td>{product.id}</td>
      <td>{product.name}</td>
      <td>{product.price}</td>
      <td>{product.description}</td>
      <td>
        <button className="btn btn-danger me-2" onClick={() => onDeleteProduct(product.id)}>
          Delete
        </button>
        <button className="btn btn-primary" onClick={() => onEditProduct(product)}>
          Edit
        </button>
      </td>
    </tr>
  );
};
