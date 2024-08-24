package org.tucno.springboot.factura.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;

/**
 * SCOPE DE LOS COMPONENTES
 * - @Component: Singleton. Se crea una única instancia del bean en el contenedor de Spring.
 * - @RequestScope: Se crea una instancia del bean por cada petición HTTP.
 * - @SessionScope: Se crea una instancia del bean por cada sesión HTTP.
 * - @ApplicationScope: Se crea una única instancia del bean por cada aplicación.
 */

@Component
// Con @RequestScope se indica que se debe crear una instancia del bean por cada petición HTTP
@RequestScope
// Con @JsonIgnoreProperties se indica que no se deben serializar las propiedades targetSource y advisors
// Esto es útil cuando se desea serializar un objeto a JSON y se quiere excluir algunas propiedades del objeto
//@JsonIgnoreProperties({"targetSource", "advisors"})
public class Invoice {
    @Autowired
    private Client client;

    @Value("${invoice.description.office}")
    private String description;

    @Autowired
    // Con @Qualifier se indica el nombre del bean que se desea inyectar en caso de que haya más de uno
    @Qualifier("default")
    private List<Item> items;

    public Invoice() {
        System.out.println("Invoice created in constructor");
    }

    public Invoice(Client client, String description, List<Item> items) {
        this.client = client;
        this.description = description;
        this.items = items;
    }

    /**
     * CLICLO DE VIDA DE UN BEAN
     * 1. Instanciación: Se crea una instancia del bean mediante el constructor.
     * 2. Inyección de dependencias: Se inyectan las dependencias mediante los métodos set.
     * 3. PostConstruct: Se ejecuta el método anotado con @PostConstruct.
     * 4. Uso del bean: Se utiliza el bean.
     * 5. PreDestroy: Se ejecuta el método anotado con @PreDestroy.
     * 6. Destrucción: Se destruye el bean.
     * 7. Recolector de basura: Se elimina la instancia del bean.
     * 8. Fin del ciclo de vida del bean.
     */

    /**
     * Con @PostConstruct se indica que el método se debe ejecutar después de que el bean ha sido creado por el contenedor de Spring
     * Las diferencias entre @PostConstruct y el constructor son:
     * - El constructor se ejecuta cuando se crea el objeto, mientras que @PostConstruct se ejecuta después de que el objeto ha sido creado por el contenedor de Spring.
     * - En el constructor no se pueden inyectar dependencias, mientras que en @PostConstruct sí.
     * - En el constructor no se puede acceder a las propiedades de la clase, mientras que en @PostConstruct sí.
     * - En el constructor no se puede acceder a los métodos de la clase, mientras que en @PostConstruct sí.
     * - En el constructor no se puede acceder a los métodos de los objetos inyectados, mientras que en @PostConstruct sí.
     */
    @PostConstruct
    public void init() {
        System.out.println("Invoice created: " + description);
        this.client.setName(client.getName().concat(" ").concat("Jose"));
        this.description = description.concat(" del cliente: ").concat(client.getName());
    }

    /**
     * Con @PreDestroy se indica que el método se debe ejecutar antes de que el bean sea destruido por el contenedor de Spring
     */
    @PreDestroy
    public void destroy() {
        System.out.println("Invoice destroyed: " + description);
    }


    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Invoice{" + "client=" + client + ", description=" + description + ", items=" + items + '}';
    }

    public Double getTotal() {
        return items.stream().mapToDouble(Item::getImporte).sum();
        // equivalent to items.stream().mapToDouble(item -> item.getImporte()).sum();
    }
}
