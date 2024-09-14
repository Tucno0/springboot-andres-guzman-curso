package org.tucno.springboot.jpa.repositories;

import org.springframework.data.repository.CrudRepository;
import org.tucno.springboot.jpa.entities.Invoice;

public interface InvoiceRepository extends CrudRepository<Invoice, Long> {
}
