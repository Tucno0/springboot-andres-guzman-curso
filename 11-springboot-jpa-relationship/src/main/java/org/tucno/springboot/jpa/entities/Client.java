package org.tucno.springboot.jpa.entities;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String lastname;

    // La anotación @OneToMany se utiliza para especificar la relación uno a muchos.
    // Puede recibir los siguientes atributos:
    // - cascade: especifica las operaciones que se propagarán a los elementos hijos.
    // - fetch: especifica la forma en que se recuperarán los elementos hijos.
    //      - FetchType.LAZY: los elementos hijos se recuperarán solo cuando se acceda a ellos.
    //      - FetchType.EAGER: los elementos hijos se recuperarán cuando se recupere la entidad padre.
    // - mappedBy: especifica el nombre de la propiedad en la entidad hijo que se utilizará para la relación.
    // - orphanRemoval: especifica si los elementos hijos deben eliminarse si se eliminan de la colección.
    // - targetEntity: especifica la clase de la entidad hijo.
    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
//            fetch = FetchType.EAGER // para evitar usar esto, se puede usar un JOIN FETCH en el query de consulta.
    )

    // La anotación @JoinColumn se utiliza para especificar la columna que se utilizará para la relación, esta será una llave foránea.
    // Puede recibir los siguientes atributos:
    // - name: especifica el nombre de la columna.
    // - referencedColumnName: especifica el nombre de la columna a la que se hace referencia.
    // Si no se agrega la anotación @JoinColumn, se creará una tabla intermedia para la relación.
//    @JoinColumn(name = "client_id")

    // La anotación @JoinTable se utiliza para especificar la tabla intermedia que se utilizará para la relación.
    // Puede recibir los siguientes atributos:
    // - name: especifica el nombre de la tabla.
    // - joinColumns: especifica las columnas que se utilizarán para la relación.
    // - inverseJoinColumns: especifica las columnas que se utilizarán para la relación inversa.
    // - uniqueConstraints: especifica las restricciones únicas que se aplicarán a la tabla.
    @JoinTable(
            name = "tbl_clients_addresses",
            joinColumns = @JoinColumn(name = "id_client"),
            inverseJoinColumns = @JoinColumn(name = "id_address"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"id_address"})
    )
//    private List<Address> addresses;
    // Se cambia a Set para evitar error de MultipleBagFetchException al usar LEFT JOIN FETCH en el query de consulta.
    // Cuando es Set o List, se debe inicializar en el constructor.
    // Cuando es Set o List el FetchType por defecto es LAZY.
    private Set<Address> addresses;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            // se pone mappedBy para indicar que la relación es bidireccional y que la entidad Invoice es la dueña de la relación.
            mappedBy = "client"
    )
//    private List<Invoice> invoices;
    // Se cambia a Set para evitar error de MultipleBagFetchException al usar LEFT JOIN FETCH en el query de consulta.
    private Set<Invoice> invoices;

    // La anotación @OneToOne se utiliza para especificar la relación uno a uno.
    // Puede recibir los siguientes atributos:
    // - cascade: especifica las operaciones que se propagarán a los elementos hijos.
    // - fetch: especifica la forma en que se recuperarán los elementos hijos.
    //      - FetchType.LAZY: los elementos hijos se recuperarán solo cuando se acceda a ellos.
    //      - FetchType.EAGER: los elementos hijos se recuperarán cuando se recupere la entidad padre.
    // - mappedBy: especifica el nombre de la propiedad en la entidad hijo que se utilizará para la relación.
    // - targetEntity: especifica la clase de la entidad hijo.
    @OneToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            // se pone mappedBy para indicar que la relación es bidireccional y que la entidad ClientDetails es la dueña de la relación.
            mappedBy = "client"
    )
    // Clase padre de la relación uno a uno.
    private ClientDetails clientDetails;

    public Client() {
        // Si se utiliza una colección en una entidad, es recomendable inicializarla en el constructor.
        addresses = new HashSet<>();
        invoices = new HashSet<>();
    }

    public Client(String name, String lastname) {
        this();
        this.name = name;
        this.lastname = lastname;
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

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Set<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }

    public Set<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(Set<Invoice> invoices) {
        this.invoices = invoices;
    }

    public ClientDetails getClientDetails() {
        return clientDetails;
    }

    public void setClientDetails(ClientDetails details) {
        this.clientDetails = details;
        clientDetails.setClient(this); // se establece la relación bidireccional.
    }

    @Override
    public String toString() {
        return "{ id: " + id
                + ", name: " + name
                + ", lastname: "+ lastname
                + ", addresses: " + addresses
                + ", invoices: " + invoices
                + ", clientDetails: " + clientDetails + " }";
    }

    public Client addInvoice(Invoice invoice) {
        invoices.add(invoice);
        invoice.setClient(this); // se establece la relación bidireccional.
        return this;
    }

    public Client addAddress(Address address) {
        addresses.add(address);
        return this;
    }

    public Client removeAddress(Address address) {
        addresses.remove(address);
        return this;
    }

    public Client removeInvoice(Invoice invoice) {
        invoices.remove(invoice);
        invoice.setClient(null); // se elimina la relación bidireccional.
        return this;
    }

    public Client removeClientDetails() {
        if (clientDetails != null) {
            clientDetails.setClient(null); // se elimina la relación bidireccional.
            clientDetails = null;
        }
        return this;
    }
}
