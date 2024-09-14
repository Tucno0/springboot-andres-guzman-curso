package org.tucno.springboot.jpa.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "invoices")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private Double total;

    @ManyToOne
    // La anotación @JoinColumn se utiliza para especificar la columna que se utilizará para la relación.
    // Si no se especifica, se utilizará el nombre de la columna con el nombre de la propiedad.
    @JoinColumn(name = "client_id")
    private Client client;

    public Invoice() {
    }

    public Invoice(String description, Double total) {
        this.description = description;
        this.total = total;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return "{ id: " + id + ", description: " + description + ", total: " + total + "}";
    }

    // equals y hashCode son necesarios para que la colección de invoices en la entidad Client funcione correctamente.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Invoice invoice = (Invoice) o;
        return Objects.equals(id, invoice.id) && Objects.equals(description, invoice.description) && Objects.equals(total, invoice.total);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, total);
    }
}
