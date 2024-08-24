package org.tucno.springboot.factura;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.tucno.springboot.factura.models.Item;
import org.tucno.springboot.factura.models.Product;

import java.util.List;

@Configuration
@PropertySource("classpath:data.properties")
public class AppConfig {

    // Los métodos que se anotan con @Bean son los que se encargan de crear los objetos que se van a inyectar en el contenedor de Spring.
    // El tipo de retorno de estos métodos es el tipo de objeto que se va a inyectar.
    // Usualmente, estos métodos se definen en una clase de configuración de Spring, la cual se anota con @Configuration.
    // Se usa la anotación @Bean en vez de la anotación @Component porque los objetos que se crean con @Bean son objetos que se pueden configurar.

    @Bean
//    @Primary
    List<Item> itemsInvoice() {
        Product product1 = new Product("Camara Sony", 100.0);
        Product product2 = new Product("Bicicleta Bianchi aro 26", 200.0);
        Product product3 = new Product("Notebook Asus", 300.0);

        Item item1 = new Item(product1, 2);
        Item item2 = new Item(product2, 1);
        Item item3 = new Item(product3, 1);

        return List.of(item1, item2, item3);
    }

    @Bean("default")
    List<Item> itemsInvoiceOffice() {
        Product product1 = new Product("Monitor LG LCD 24", 200.0);
        Product product2 = new Product("Notebook Dell", 500.0);
        Product product3 = new Product("Impresora HP Multifuncional", 80.0);
        Product product4 = new Product("Escritorio Oficina", 300.0);
        Product product5 = new Product("Silla Oficina", 150.0);

        Item item1 = new Item(product1, 2);
        Item item2 = new Item(product2, 5);
        Item item3 = new Item(product3, 1);
        Item item4 = new Item(product4, 2);
        Item item5 = new Item(product5, 2);

        return List.of(item1, item2, item3, item4, item5);
    }


}
