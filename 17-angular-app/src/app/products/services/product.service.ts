import { Injectable } from '@angular/core';
import { Product } from '../models/product.model';
import { Observable, of } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  public products: Product[] = [
    {
      id: 1,
      name: 'Product 1',
      description: 'This is a description for product 1',
      price: 100
    },
    {
      id: 2,
      name: 'Product 2',
      description: 'This is a description for product 2',
      price: 200
    },
    {
      id: 3,
      name: 'Product 3',
      description: 'This is a description for product 3',
      price: 300
    }
  ];

  constructor() { }

  findAll(): Observable<Product[]> {
    return of(this.products);
  }
}
