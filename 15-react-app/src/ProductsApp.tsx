import { useEffect, useState } from "react";
import { Product } from "./interfaces/product.interface";
import { initProducts } from "./services/product.service";
import { ProductGrid } from "./components/ProductGrid";
import { ProductForm } from "./components/ProductForm";

export const ProductsApp = () => {
  const [products, setProducts] = useState<Product[]>([]);
  const [selectedProduct, setSelectedProduct] = useState<Product>({
    id: "",
    name: "",
    price: 0,
    description: "",
  });

  useEffect(() => {
    setProducts(initProducts);
  }, []);

  const handleAddProduct = (newProduct: Product) => {
    if (products.some((prod) => prod.id === newProduct.id)) {
      setProducts(
        products.map((prod) =>
          prod.id === newProduct.id ? { ...newProduct } : prod
        )
      );
    } else {
      setProducts([...products, newProduct]);
    }
  };

  const handleDeleteProduct = (id: string) => {
    setProducts(products.filter((product) => product.id !== id));
  };

  const handleSelectProduct = (product: Product) => {
    setSelectedProduct({ ...product });
  };

  return (
    <>
      <h1 className="text-center mb-4">Products</h1>

      <div className="container">
        <div className="row">
          <div className="col-md-6 mb-3">
            {products.length === 0 ? (
              <div className="alert alert-warning">No products available</div>
            ) : (
              <ProductGrid
                products={products}
                onDeleteProduct={handleDeleteProduct}
                onEditProduct={handleSelectProduct}
              />
            )}
          </div>

          <div className="col-md-6 mb-3">
            <ProductForm
              onAddProduct={handleAddProduct}
              selectedProduct={selectedProduct}
            />
          </div>
        </div>
      </div>
    </>
  );
};
