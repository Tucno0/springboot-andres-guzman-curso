package org.tucno.springboot.factura.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.annotation.SessionScope;

@Component
// Con @SessionScope se indica que se debe crear una instancia del bean por cada sesión HTTP
// Por ejemplo, si se abre una pestaña en el navegador, se crea una sesión HTTP y se crea una instancia del bean
// y si se abre otra pestaña en el navegador, se crea otra sesión HTTP y otra instancia del bean
@RequestScope
//@JsonIgnoreProperties({"targetSource", "advisors"})
public class Client {

    @Value("${client.name}")
    private String name;

    @Value("${client.lastName}")
    private String lastName;

    public Client() {
    }

    public Client(String name, String lastName) {
        this.name = name;
        this.lastName = lastName;
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

    @Override
    public String toString() {
        return "Client{" + "name=" + name + ", lastName=" + lastName + '}';
    }
}
