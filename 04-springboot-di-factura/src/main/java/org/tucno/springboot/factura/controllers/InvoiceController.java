package org.tucno.springboot.factura.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tucno.springboot.factura.models.Client;
import org.tucno.springboot.factura.models.Invoice;

@RestController
@RequestMapping("/invoices")
public class InvoiceController {
    @Autowired
    private Invoice invoice;

//    @GetMapping("/show")
//    public Invoice show() {
//        return invoice;
//    }

    // Se crea un nuevo objeto Invoice con los datos del objeto invoice y se retorna
    // este nuevo objeto. De esta forma, se evita que se modifiquen los datos del objeto invoice original al mostrarlos.
    @GetMapping("/show")
    public Invoice show() {
        Invoice invoice = new Invoice();

        Client client = new Client();
        client.setName(this.invoice.getClient().getName());
        client.setLastName(this.invoice.getClient().getLastName());

        invoice.setDescription(this.invoice.getDescription());
        invoice.setItems(this.invoice.getItems());
        invoice.setClient(client);

        return invoice;
    }
}
