import { Component, inject, OnInit } from '@angular/core';
import { ProductService } from '../services/product.service';
import { Product } from '../models/product.model';
import { FormComponent } from './form/form.component';

@Component({
  selector: 'app-product',
  imports: [FormComponent],
  templateUrl: './product.component.html',
  styleUrl: './product.component.css',
})
export class ProductComponent implements OnInit {
  private productsService = inject(ProductService);

  public products: Product[] = [];
  public selectedProduct: Product = {
    id: 0,
    name: '',
    description: '',
    price: 0,
  };

  ngOnInit() {
    this.productsService.findAll().subscribe((products) => {
      this.products = products;
    });
  }

  onProductCreated(product: Product): void {
    if (product.id > 0) {
      this.products = this.products.map((p) => {
        if (p.id === product.id) {
          return { ...product };
        }
        return p;
      });
    } else {
      product.id = this.products.length + 1;
      this.products = [
        ...this.products,
        { ...product, id: this.products.length + 1 },
      ];
    }
  }

  onEditProduct(product: Product): void {
    this.selectedProduct = { ...product };
  }

  onDeleteProduct(productId: number): void {
    this.products = this.products.filter((p) => p.id !== productId);
  }
}
