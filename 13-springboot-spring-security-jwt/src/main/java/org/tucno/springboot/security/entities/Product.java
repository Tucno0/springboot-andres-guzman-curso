package org.tucno.springboot.security.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.tucno.springboot.security.validators.ExistsInDb;
import org.tucno.springboot.security.validators.IsRequired;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ExistsInDb
    @IsRequired
    private String sku;

    @NotEmpty(message = "{NotEmpty.product.name}")
    @Size(min = 3, max = 50)
    private String name;

    @Min(500)
    @NotNull
    private Double price;

//    @NotBlank
    @IsRequired( message = "{IsRequired.product.description}") // Validación personalizada
    private String description;

    public Product() {
    }

    public Product(String name, Double price) {
        this.name = name;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
