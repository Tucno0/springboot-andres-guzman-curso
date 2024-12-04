package org.tucno.springboot.security.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.tucno.springboot.security.validators.ExistsByUsername;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    @ExistsByUsername // Validación personalizada
    @NotBlank
    @Size(min = 4, max = 12)
    private String username;

    @Column(nullable = false)
    @NotBlank
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // Indica que el campo no se debe incluir en la respuesta JSON
    private String password;

    // JsonIgnoreProperties Excluye el campo en el Request y Response JSON (no se debe incluir en la respuesta JSON)
    // Para evitar la recursividad en la serialización de objetos, se debe excluir la propiedad users de la serialización
    @JsonIgnoreProperties({"users", "handler", "hibernateLazyInitializer"})
    // Solo podremos obtener los roles de un usuario, no los usuarios de un rol
    // Si queremos obtener los usuarios de un rol, debemos hacerlo desde el lado de Role
    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"),
            uniqueConstraints = { @UniqueConstraint(columnNames = {"user_id", "role_id"}) }
    )
    private List<Role> roles;

    private boolean enabled;

    @Transient // Indica que el campo no se mapea a la base de datos
//    @JsonIgnore // Excluye el campo en el Request y Response JSON
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // Indica que el campo no se debe incluir en la respuesta JSON
    private boolean admin;

    public User() {
        this.roles = new ArrayList<>();
    }

    @PrePersist
    public void prePersist() {
        this.enabled = true;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    @Override
    public String toString() {
        return "{ id: " + id + ", username: " + username + ", password: " + password + ", roles: " + roles + ", enabled: " + enabled + " }";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username);
    }
}
