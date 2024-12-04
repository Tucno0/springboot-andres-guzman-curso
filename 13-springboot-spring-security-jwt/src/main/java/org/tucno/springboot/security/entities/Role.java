package org.tucno.springboot.security.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    // Para evitar la recursividad en la serialización de objetos, se debe excluir la propiedad roles de la serialización
    // hanlder y hibernateLazyInitializer son propiedades que se generan automáticamente por Hibernate, se deben excluir de la serialización para evitar errores
    @JsonIgnoreProperties({"roles", "handler", "hibernateLazyInitializer"})
    // La anotación @ManyToMany indica que la relación entre las entidades User y Role es de muchos a muchos
    // mappedBy indica que la relación es bidireccional y que el propietario de la relación es la entidad User
    @ManyToMany(mappedBy = "roles")
    private List<User> users;

    public Role() {
        this.users = new ArrayList<>();
    }

    public Role(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "{ id: " + id + ", name: " + name + " }";
    }

    // Creamos los métodos equals() y hashCode() para comparar objetos de tipo Role
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(id, role.id) && Objects.equals(name, role.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
