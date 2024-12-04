package org.tucno.springboot.security.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.tucno.springboot.security.validators.ExistsInDb;
import org.tucno.springboot.security.validators.IsRequired;
import org.tucno.springboot.security.validators.groups.OnCreate;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ExistsInDb(groups = {OnCreate.class}) // groups = {OnCreate.class} indica que la validación se ejecuta solo en el grupo OnCreate
    @IsRequired(groups = {OnCreate.class})
    private String sku;

    @NotEmpty(message = "{NotEmpty.product.name}", groups = {OnCreate.class})
    @Size(min = 3, max = 50, groups = {OnCreate.class})
    private String name;

    @Min(value = 500, groups = {OnCreate.class})
    @NotNull(groups = {OnCreate.class})
    private Double price;

//    @NotBlank
    @IsRequired( message = "{IsRequired.product.description}", groups = {OnCreate.class}) // Validación personalizada
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
