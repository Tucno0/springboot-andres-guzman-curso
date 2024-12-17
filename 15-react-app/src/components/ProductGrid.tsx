import { Product } from "../interfaces/product.interface";
import { ProductDetail } from "./ProductDetail";

interface Props {
  products: Product[];
  onDeleteProduct: (id: string) => void;
  onEditProduct: (product: Product) => void;
}

export const ProductGrid = ({
  products = [],
  onDeleteProduct,
  onEditProduct,
}: Props) => {
  return (
    <table className="table table-striped mt-4">
      <thead>
        <tr>
          <th>ID</th>
          <th>Name</th>
          <th>Price</th>
          <th>Description</th>
          <th>Actions</th>
        </tr>
      </thead>
      <tbody>
        {products.map((product) => (
          <ProductDetail
            key={product.id}
            product={product}
            onDeleteProduct={onDeleteProduct}
            onEditProduct={onEditProduct}
          />
        ))}
      </tbody>
    </table>
  );
};
