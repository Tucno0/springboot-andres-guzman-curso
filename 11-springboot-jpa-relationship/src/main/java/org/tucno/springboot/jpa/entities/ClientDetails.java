package org.tucno.springboot.jpa.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "clients_details")
public class ClientDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean premium;
    private Integer points;

    @OneToOne
    // La anotación @JoinColumn se utiliza para especificar la columna que se utilizará para la relación.
    // Si no se especifica, se utilizará el nombre de la columna con el nombre de la propiedad.
    // La clase que usa la anotación @JoinColumn es la dueña de la relación.
    @JoinColumn(name = "id_client_detail")
    // Por defecto, en una relación uno a uno, la entidad que tiene la llave foránea es la dueña de la relación.
    // Por defecto el FetchType es EAGER.
    // Clase hija es la que tiene la llave foránea y es la dueña de la relación.
    private Client client;

    public ClientDetails() {
    }

    public ClientDetails(boolean premium, Integer points) {
        this.premium = premium;
        this.points = points;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isPremium() {
        return premium;
    }

    public void setPremium(boolean premium) {
        this.premium = premium;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return "{ id: " + id + ", premium: " + premium + ", points: " + points + " }";
    }
}
