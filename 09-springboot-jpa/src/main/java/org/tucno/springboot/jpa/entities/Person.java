package org.tucno.springboot.jpa.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "persons")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String lastname;

    @Column(name = "programming_language")
    private String programmingLanguage;

    // Se incrusta la clase Audit en la entidad Person para que las propiedades de la clase Audit se mapeen a las columnas de la tabla de la entidad Person.
    @Embedded
    private Audit audit = new Audit();

    // JPA necesita un constructor vacío
    public Person() {
    }

    public Person(String name, String lastname) {
        this.name = name;
        this.lastname = lastname;
    }

    public Person(Long id, String name, String lastname, String programmingLanguage) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.programmingLanguage = programmingLanguage;
    }

    // EVENTOS DE CICLO DE VIDA DE UNA ENTIDAD

    /**
     * Método que se ejecuta antes de persistir un objeto en la base de datos
     * Se puede utilizar para realizar operaciones previas a la persistencia
     * como validar datos, asignar valores por defecto, etc.
     */
    @PrePersist
    public void prePersist() {
        System.out.println("Antes de persistir");
    }

    /**
     * Método que se ejecuta después de persistir un objeto en la base de datos
     * Se puede utilizar para realizar operaciones posteriores a la persistencia
     * como enviar correos, notificaciones, etc.
     */
    @PostPersist
    public void postPersist() {
        System.out.println("Después de persistir");
    }

    /**
     * Método que se ejecuta antes de actualizar un objeto en la base de datos
     * Se puede utilizar para realizar operaciones previas a la actualización
     * como validar datos, asignar valores por defecto, etc.
     */
    @PreUpdate
    public void preUpdate() {
        System.out.println("Antes de actualizar");
    }

    /**
     * Método que se ejecuta después de actualizar un objeto en la base de datos
     * Se puede utilizar para realizar operaciones posteriores a la actualización
     * como enviar correos, notificaciones, etc.
     */
    @PostUpdate
    public void postUpdate() {
        System.out.println("Después de actualizar");
    }

    /**
     * Método que se ejecuta antes de eliminar un objeto en la base de datos
     * Se puede utilizar para realizar operaciones previas a la eliminación
     * como validar datos, asignar valores por defecto, etc.
     */
    @PreRemove
    public void preRemove() {
        System.out.println("Antes de eliminar");
    }

    /**
     * Método que se ejecuta después de eliminar un objeto en la base de datos
     * Se puede utilizar para realizar operaciones posteriores a la eliminación
     * como enviar correos, notificaciones, etc.
     */
    @PostRemove
    public void postRemove() {
        System.out.println("Después de eliminar");
    }

    /**
     * Método que se ejecuta después de cargar un objeto de la base de datos
     * Se puede utilizar para realizar operaciones posteriores a la carga
     * como enviar correos, notificaciones, etc.
     */
    @PostLoad
    public void postLoad() {
        System.out.println("Después de cargar");
    }


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    public String getProgrammingLanguage() {
        return programmingLanguage;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setProgrammingLanguage(String programmingLanguage) {
        this.programmingLanguage = programmingLanguage;
    }

    @Override
    public String toString() {
        return "{ id=" + id + ", name=" + name + ", lastName=" + lastname + ", programmingLanguage=" + programmingLanguage + ", createdAt=" + audit.getCreatedAt() + ", updatedAt=" + audit.getUpdatedAt() + " }";
    }
}
