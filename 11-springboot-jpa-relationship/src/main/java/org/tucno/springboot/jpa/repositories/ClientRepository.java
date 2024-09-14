package org.tucno.springboot.jpa.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.tucno.springboot.jpa.entities.Client;

import java.util.Optional;

public interface ClientRepository extends CrudRepository<Client, Long> {
    @Query("SELECT c FROM Client c LEFT JOIN FETCH c.addresses WHERE c.id = ?1")
    Optional<Client> findOneWithAddresses(Long id);

    // Se puede usar JOIN FETCH en el query de consulta para evitar el uso de FetchType.EAGER.
    // Se usa LEFT JOIN FETCH para que se traigan los datos de la entidad Client aunque no tenga direcciones.
    @Query("SELECT c FROM Client c LEFT JOIN FETCH c.invoices WHERE c.id = ?1")
    Optional<Client> findOneWithInvoices(Long id);

    // Se puede usar JOIN FETCH en el query de consulta para evitar el uso de FetchType.EAGER.
    // Se usa LEFT JOIN FETCH para que se traigan los datos de la entidad Client aunque no tenga direcciones ni facturas.
    @Query("SELECT c FROM Client c LEFT JOIN FETCH c.addresses LEFT JOIN FETCH c.invoices LEFT JOIN FETCH c.clientDetails WHERE c.id = ?1")
    Optional<Client> findOne(Long id);
}
