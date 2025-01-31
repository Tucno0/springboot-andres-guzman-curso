export interface ProductsResponse {
  _embedded: Embedded;
  _links: ProductsResponseLinks;
}

export interface Embedded {
  products: ProductResponse[];
}

export interface ProductResponse {
  id: number;
  name: string;
  description: string;
  price: number;
  _links: ProductLinks;
}

export interface ProductLinks {
  self: Profile;
  product: Profile;
}

export interface Profile {
  href: string;
}

export interface ProductsResponseLinks {
  self: Profile;
  profile: Profile;
}
