import { inject, Injectable } from '@angular/core';
import { Product } from '../models/product.model';
import { map, Observable, of } from 'rxjs';
import { environment } from '@/environments/environment';
import { HttpClient } from '@angular/common/http';
import {
  ProductResponse,
  ProductsResponse,
} from '../interfaces/products-response.interface';

@Injectable({
  providedIn: 'root',
})
export class ProductService {
  public products: Product[] = [
    {
      id: 1,
      name: 'Product 1',
      description: 'This is a description for product 1',
      price: 100,
    },
    {
      id: 2,
      name: 'Product 2',
      description: 'This is a description for product 2',
      price: 200,
    },
    {
      id: 3,
      name: 'Product 3',
      description: 'This is a description for product 3',
      price: 300,
    },
  ];

  private baseUrl = environment.baseUrl;
  private http = inject(HttpClient);

  constructor() {}

  findAll(): Observable<Product[]> {
    // return of(this.products);
    return this.http
      .get<ProductsResponse>(`${this.baseUrl}/products`)
      .pipe(map((response) => response._embedded.products));
  }

  create(product: Product): Observable<Product> {
    const { id, ...productWithoutId } = product;
    return this.http.post<ProductResponse>(
      `${this.baseUrl}/products`,
      productWithoutId
    );
  }

  update(product: Product): Observable<Product> {
    return this.http.put<ProductResponse>(
      `${this.baseUrl}/products/${product.id}`,
      product
    );
  }

  delete(id: number): Observable<Product> {
    return this.http.delete<ProductResponse>(`${this.baseUrl}/products/${id}`);
  }
}
