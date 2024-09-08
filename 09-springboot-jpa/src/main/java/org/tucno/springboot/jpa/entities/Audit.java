package org.tucno.springboot.jpa.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

// La anotación @Embeddable se utiliza para marcar una clase como una clase incrustable.
// Una clase incrustable es una clase cuyos objetos se almacenan como parte de la entidad que contiene la clase incrustable.
// La clase incrustable no tiene una identidad propia y no se puede almacenar en una tabla de la base de datos.
// En su lugar, los objetos de la clase incrustable se almacenan como parte de la entidad que contiene la clase incrustable.
// La clase incrustable se mapea a las columnas de la tabla de la entidad que contiene la clase incrustable.
// La clase incrustable se puede utilizar para agrupar varias propiedades en una sola propiedad.
@Embeddable
public class Audit {
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // EVENTOS DE CICLO DE VIDA DE UNA ENTIDAD
    @PrePersist
    public void prePersist() {
        System.out.println("Antes de persistir");
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        System.out.println("Antes de actualizar");
        this.updatedAt = LocalDateTime.now();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
