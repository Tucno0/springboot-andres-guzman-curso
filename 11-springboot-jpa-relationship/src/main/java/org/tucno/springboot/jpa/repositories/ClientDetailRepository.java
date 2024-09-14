package org.tucno.springboot.jpa.repositories;

import org.springframework.data.repository.CrudRepository;
import org.tucno.springboot.jpa.entities.ClientDetails;

public interface ClientDetailRepository extends CrudRepository<ClientDetails, Long> {

}
