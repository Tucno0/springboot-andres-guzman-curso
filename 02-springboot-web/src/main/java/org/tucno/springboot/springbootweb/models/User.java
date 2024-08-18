package org.tucno.springboot.springbootweb.models;

public class User {
    String name;
    String lastName;
    String email;

    public User() {
    }

    public User(String name, String lastName) {
        this.name = name;
        this.lastName = lastName;
    }

    public User(String name, String lastName, String email) {
        // Se puede llamar a otro constructor de la clase con la palabra clave this y los parámetros necesarios
        this(name, lastName);
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
