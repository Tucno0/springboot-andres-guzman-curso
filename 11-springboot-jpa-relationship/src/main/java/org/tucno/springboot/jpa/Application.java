package org.tucno.springboot.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;
import org.tucno.springboot.jpa.entities.*;
import org.tucno.springboot.jpa.repositories.*;

import java.util.*;

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private ClientDetailRepository clientDetailRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//        manyToOneCreateClient();
//        manyToOneFindByClientId();
//        oneToMany();
//        oneToManyFindById();
//        removeAddress();
//        removeAddressFindById();
//        oneToManyBidirectional();
//        oneToManyBidirectionalFindById();
//        oneToManyBidirectionalFindOne();
//        removeInvoiceBidirectionalFindOne();
//        removeInvoiceBidirectional();
//        oneToOne();
//        oneToOneFindById();
//        oneToOneBidirectional();
//        oneToOneBidirectionalFindById();
//        manyToMany();
//        manyToManyFind();
//        manyToManyRemoveFind();
//        manyToManyRemove();
//        manyToManyBidirectional();
//        manyToManyBidirectionalRemove();
//        manyToManyBidirectionalFind();
        manyToManyBidirectionalRemoveFind();
    }

    @Transactional
    public void manyToOneCreateClient() {
        Client client = new Client("Goku", "Son");
        clientRepository.save(client);

        Invoice invoice = new Invoice("Compras de oficina", 2000.0);
        invoice.setClient(client);
        Invoice invoiceDb = invoiceRepository.save(invoice);
        System.out.println("invoiceDb = " + invoiceDb);
    }

    @Transactional
    public void manyToOneFindByClientId() {
        Optional<Client> clientOptional = clientRepository.findById(1L);

        if (clientOptional.isPresent()) {
            Client client = clientOptional.get();

            Invoice invoice = new Invoice("Compras de casa", 5000.0);
            invoice.setClient(client);
            Invoice invoiceDb = invoiceRepository.save(invoice);
            System.out.println("invoiceDb = " + invoiceDb);
        }
    }

    @Transactional
    public void oneToMany() {
        Client client = new Client("Goku", "Son");

        Address address1 = new Address("Calle 1", 123);
        Address address2 = new Address("Calle 2", 456);

        client.getAddresses().add(address1);
        client.getAddresses().add(address2);

        // Cuando se guarda un cliente, también se guardan las direcciones.
        // porque se especificó CascadeType.ALL en la relación.
        clientRepository.save(client);

        System.out.println("client = " + client);
    }

    @Transactional
    public void oneToManyFindById() {
        clientRepository.findById(1L).ifPresent(client -> {
            Address address1 = new Address("Calle 1", 123);
            Address address2 = new Address("Calle 2", 456);

//            client.setAddresses(Arrays.asList(address1, address2));
            client.setAddresses(new HashSet<>(Arrays.asList(address1, address2)));

            // Cuando se guarda un cliente, también se guardan las direcciones.
            // porque se especificó CascadeType.ALL en la relación.
            clientRepository.save(client);

            System.out.println("client = " + client);
        });
    }

    @Transactional
    public void removeAddress() {
        Client client = new Client("Goku", "Son");

        Address address1 = new Address("Calle 1", 123);
        Address address2 = new Address("Calle 2", 456);

        client.getAddresses().add(address1);
        client.getAddresses().add(address2);

        // Cuando se guarda un cliente, también se guardan las direcciones.
        // porque se especificó CascadeType.ALL en la relación.
        clientRepository.save(client);

        System.out.println("client = " + client);

        Optional<Client> clientOptional = clientRepository.findById(13L);

        // Se obtiene la dirección que se desea eliminar.
        clientOptional.ifPresent(client1 -> {
            client1.getAddresses().remove(address1);
            clientRepository.save(client1);

            System.out.println("client1 = " + client1);
        });
    }

    @Transactional
    public void removeAddressFindById() {
        clientRepository.findById(12L).ifPresent(client -> {
            Address address1 = new Address("Calle 1", 123);
            Address address2 = new Address("Calle 2", 456);

//            client.setAddresses(Arrays.asList(address1, address2));
            client.setAddresses(new HashSet<>(Arrays.asList(address1, address2)));

            // Cuando se guarda un cliente, también se guardan las direcciones.
            // porque se especificó CascadeType.ALL en la relación.
            clientRepository.save(client);

            System.out.println("client = " + client);

//            Optional<Client> clientOptional = clientRepository.findById(12L);
            Optional<Client> clientOptional = clientRepository.findOneWithAddresses(12L);

            // Se obtiene la dirección que se desea eliminar.
            clientOptional.ifPresent(client1 -> {
                // Se elimina la dirección en la posición 1 (Address 2).
                client1.getAddresses().remove(address1);

//                client1.getAddresses().remove(address2); // No se elimina porque no es el mismo objeto.

                clientRepository.save(client1);

                System.out.println("client1 = " + client1);
            });
        });
    }

    @Transactional
    public void oneToManyBidirectional() {
        Client client = new Client("Goku", "Son");

        Invoice invoice1 = new Invoice("Compras de oficina", 2000.0);
        Invoice invoice2 = new Invoice("Compras de casa", 5000.0);
        Invoice invoice3 = new Invoice("Compras de auto", 10000.0);

        // Se agregan las facturas al cliente.
//        List<Invoice> invoices = Arrays.asList(invoice1, invoice2, invoice3);
//        client.setInvoices(invoices);

        // Se asigna el cliente a cada factura. Esto es necesario para que la relación sea bidireccional.
//        invoice1.setClient(client);
//        invoice2.setClient(client);
//        invoice3.setClient(client);

        // Forma más sencilla de hacerlo con un método en la entidad Client.
        // Hace lo mismo que las líneas anteriores.
        client.addInvoice(invoice1).addInvoice(invoice2).addInvoice(invoice3);

        // Se guardan las facturas y el cliente.
        clientRepository.save(client);

        System.out.println("client = " + client);
    }

    @Transactional
    public void oneToManyBidirectionalFindById() {
        Optional<Client> clientOptional = clientRepository.findOneWithInvoices(1L);

        clientOptional.ifPresentOrElse(client -> {
            Invoice invoice1 = new Invoice("Compras de oficina", 2000.0);
            Invoice invoice2 = new Invoice("Compras de casa", 5000.0);
            Invoice invoice3 = new Invoice("Compras de auto", 10000.0);

            // Se agregan las facturas al cliente.
            client.addInvoice(invoice1).addInvoice(invoice2).addInvoice(invoice3);

            // Se guardan las facturas y el cliente.
            clientRepository.save(client);

            System.out.println("client = " + client);
        }, () -> System.out.println("Cliente no encontrado."));
    }

    @Transactional
    public void oneToManyBidirectionalFindOne() {
        Optional<Client> clientOptional = clientRepository.findOne(1L);

        clientOptional.ifPresentOrElse(client -> {
            Invoice invoice1 = new Invoice("Compras de oficina", 2000.0);
            Invoice invoice2 = new Invoice("Compras de casa", 5000.0);
            Invoice invoice3 = new Invoice("Compras de auto", 10000.0);

            // Se agregan las facturas al cliente.
            client.addInvoice(invoice1).addInvoice(invoice2).addInvoice(invoice3);

            // Se guardan las facturas y el cliente.
            clientRepository.save(client);

            // Recuperar el cliente de nuevo para asegurarse de que los IDs se hayan generado.
            Client savedClient = clientRepository
                    .findOne(1L)
                    .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

            System.out.println("client = " + savedClient);
        }, () -> System.out.println("Cliente no encontrado."));
    }

    @Transactional
    public void removeInvoiceBidirectionalFindOne() {
        Optional<Client> clientOptional1 = clientRepository.findOne(1L);

        clientOptional1.ifPresentOrElse(client -> {
            Invoice invoice1 = new Invoice("Compras de oficina", 2000.0);
            Invoice invoice2 = new Invoice("Compras de casa", 5000.0);
            Invoice invoice3 = new Invoice("Compras de auto", 10000.0);

            // Se agregan las facturas al cliente.
            client.addInvoice(invoice1).addInvoice(invoice2).addInvoice(invoice3);

            // Se guardan las facturas y el cliente.
            clientRepository.save(client);

            // Recuperar el cliente de nuevo para asegurarse de que los IDs se hayan generado.
            Client savedClient = clientRepository
                    .findOne(1L)
                    .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

            System.out.println("client1 = " + savedClient);
        }, () -> System.out.println("Cliente no encontrado."));

        Optional<Client> clientOptional2 = clientRepository.findOne(1L);

        clientOptional2.ifPresentOrElse(client -> {
            Optional<Invoice> invoiceOptional = invoiceRepository.findById(1L);

            invoiceOptional.ifPresent(invoice -> {
                client.removeInvoice(invoice);
                clientRepository.save(client);

                System.out.println("client2 = " + client);
            });

        }, () -> System.out.println("Cliente no encontrado."));
    }

    @Transactional
    public void removeInvoiceBidirectional() {
        Client client = new Client("Goku", "Son");

        Optional<Client> clientOptional1 = Optional.of(client);

        clientOptional1.ifPresentOrElse(clientOpt -> {
            Invoice invoice1 = new Invoice("Compras de oficina", 2000.0);
            Invoice invoice2 = new Invoice("Compras de casa", 5000.0);
            Invoice invoice3 = new Invoice("Compras de auto", 10000.0);

            // Se agregan las facturas al cliente.
            clientOpt.addInvoice(invoice1).addInvoice(invoice2).addInvoice(invoice3);

            // Se guardan las facturas y el cliente.
            clientRepository.save(clientOpt);

            System.out.println("client1 = " + clientOpt);
        }, () -> System.out.println("Cliente no encontrado."));

        Optional<Client> clientOptional2 = clientRepository.findOne(13L);

        clientOptional2.ifPresentOrElse(clientDb-> {
            Optional<Invoice> invoiceOptional = invoiceRepository.findById(1L);

            invoiceOptional.ifPresent(invoice -> {
                clientDb.removeInvoice(invoice);
                clientRepository.save(clientDb);

                System.out.println("client2 = " + clientDb);
            });

        }, () -> System.out.println("Cliente no encontrado."));
    }

    @Transactional
    public void oneToOne() {
        ClientDetails clientDetails = new ClientDetails(true, 10000);
        clientDetailRepository.save(clientDetails);

        Client client = new Client("Goku", "Son");
        client.setClientDetails(clientDetails);
        clientRepository.save(client);

        System.out.println("client = " + client);
    }

    @Transactional
    public void oneToOneFindById() {
        ClientDetails clientDetails = new ClientDetails(true, 10000);
        clientDetailRepository.save(clientDetails);

        Client client = clientRepository
                .findOne(1L)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        client.setClientDetails(clientDetails);
        clientRepository.save(client);

        System.out.println("client = " + client);
    }

    @Transactional
    public void oneToOneBidirectional() {
        Client client = new Client("Vegeta", "Prince");
        ClientDetails clientDetails = new ClientDetails(true, 10000);

        client.setClientDetails(clientDetails);
        clientDetails.setClient(client);

        // Solo se guarda el cliente, ya que la relación bidireccional se encarga de guardar el clienteDetails.
        // Y que la Clase cliente tiene el cascade = CascadeType.ALL.
        clientRepository.save(client);

        System.out.println("client = " + client);
    }

    @Transactional
    public void oneToOneBidirectionalFindById() {
        Client client = clientRepository
                .findOne(1L)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        ClientDetails clientDetails = new ClientDetails(true, 10000);

        client.setClientDetails(clientDetails);

        // Solo se guarda el cliente, ya que la relación bidireccional se encarga de guardar el clienteDetails.
        // Y que la Clase cliente tiene el cascade = CascadeType.ALL.
        clientRepository.save(client);

        System.out.println("client = " + client);
    }

    @Transactional
    public void manyToMany() {
        Student student1 = new Student("Vegeta", "Prince");
        Student student2 = new Student("Krillin", "Orin");

        Course course1 = new Course("Curso de java master", "Andres Guzman");
        Course course2 = new Course("Curso de spring boot", "Andres Guzman");

        // Se asignan los cursos a los estudiantes.
        student1.setCourses(Set.of(course1, course2));
        student2.setCourses(Set.of(course2));

        // Se guardan los estudiantes y los cursos.
//        studentRepository.saveAll(Set.of(student1, student2));
        studentRepository.saveAll(List.of(student1, student2));

        System.out.println("student1 = " + student1);
        System.out.println("student2 = " + student2);
    }

    @Transactional
    public void manyToManyFind() {
        Student student1 = studentRepository
                .findById(1L)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));

        Student student2 = studentRepository
                .findById(2L)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));

        Course course1 = courseRepository
                .findById(1L)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));

        Course course2 = courseRepository
                .findById(2L)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));

        Course course3 = courseRepository
                .findById(3L)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));

        // Se asignan los cursos a los estudiantes.
        student1.setCourses(Set.of(course1, course2));
        student2.setCourses(Set.of(course2, course3));

        // Se guardan los estudiantes y los cursos.
        studentRepository.saveAll(List.of(student1, student2));

        System.out.println("student1 = " + student1);
        System.out.println("student2 = " + student2);
    }

    @Transactional
    public void manyToManyRemoveFind() {
        Student student1 = studentRepository
                .findById(1L)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));

        Student student2 = studentRepository
                .findById(2L)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));

        Course course1 = courseRepository
                .findById(1L)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));

        Course course2 = courseRepository
                .findById(2L)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));

        Course course3 = courseRepository
                .findById(3L)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));

        // Se asignan los cursos a los estudiantes.
        student1.setCourses(Set.of(course1, course2, course3));
        student2.setCourses(Set.of(course2, course3));

        // Se guardan los estudiantes y los cursos.
        studentRepository.saveAll(List.of(student1, student2));

        System.out.println("student1 = " + student1);
        System.out.println("student2 = " + student2);

        // Se eliminan los cursos de los estudiantes.
        Student student1Db = studentRepository
                .findOneWithCourses(1L)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));

        Course courseDb = courseRepository
                .findById(3L)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));

        student1Db.getCourses().remove(courseDb);
        studentRepository.save(student1Db);

        System.out.println("student1Db = " + student1Db);
    }

    @Transactional
    public void manyToManyRemove() {
        Student student1 = new Student("Vegeta", "Prince");
        Student student2 = new Student("Krillin", "Orin");

        Course course1 = new Course("Curso de java master", "Andres Guzman");
        Course course2 = new Course("Curso de spring boot", "Andres Guzman");

        // Se asignan los cursos a los estudiantes.
        student1.setCourses(Set.of(course1, course2));
        student2.setCourses(Set.of(course2));

        // Se guardan los estudiantes y los cursos.
//        studentRepository.saveAll(Set.of(student1, student2));
        studentRepository.saveAll(List.of(student1, student2));

        System.out.println("student1 = " + student1);
        System.out.println("student2 = " + student2);

        // Se eliminan los cursos de los estudiantes.
        Student student1Db = studentRepository
                .findOneWithCourses(5L)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));

        Course courseDb = courseRepository
                .findById(6L)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));

        student1Db.getCourses().remove(courseDb);
        studentRepository.save(student1Db);

        System.out.println("student1Db = " + student1Db);
    }

    @Transactional
    public void manyToManyBidirectional() {
        Student student1 = new Student("Vegeta", "Prince");
        Student student2 = new Student("Krillin", "Orin");

        Course course1 = new Course("Curso de java master", "Andres Guzman");
        Course course2 = new Course("Curso de spring boot", "Andres Guzman");

        // Se asignan los cursos a los estudiantes.
        student1.addCourse(course1).addCourse(course2);
        student2.addCourse(course2);

        // Se guardan los estudiantes y los cursos.
//        studentRepository.saveAll(Set.of(student1, student2));
        studentRepository.saveAll(List.of(student1, student2));

        System.out.println("student1 = " + student1);
        System.out.println("student2 = " + student2);
    }

    @Transactional
    public void manyToManyBidirectionalRemove() {
        Student student1 = new Student("Vegeta", "Prince");
        Student student2 = new Student("Krillin", "Orin");

        Course course1 = new Course("Curso de java master", "Andres Guzman");
        Course course2 = new Course("Curso de spring boot", "Andres Guzman");

        // Se asignan los cursos a los estudiantes.
        student1.addCourse(course1).addCourse(course2);
        student2.addCourse(course2);

        // Se guardan los estudiantes y los cursos.
        studentRepository.saveAll(List.of(student1, student2));

        System.out.println("student1 = " + student1);
        System.out.println("student2 = " + student2);

        // Se eliminan los cursos de los estudiantes.
        Student student1Db = studentRepository
                .findOneWithCourses(5L)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));

        Course courseDb = courseRepository
                .findById(6L)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));

        student1Db.removeCourse(courseDb);
        studentRepository.save(student1Db);

        System.out.println("student1Db = " + student1Db);
    }

    @Transactional
    public void manyToManyBidirectionalFind() {
        Student student1 = studentRepository
                .findOneWithCourses(1L)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));

        Student student2 = studentRepository
                .findOneWithCourses(2L)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));

        Course course1 = courseRepository
                .findById(1L)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));

        Course course2 = courseRepository
                .findById(2L)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));

        Course course3 = courseRepository
                .findById(3L)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));

        // Se asignan los cursos a los estudiantes.
        student1.addCourse(course1).addCourse(course2);
        student2.addCourse(course2).addCourse(course3);

        // Se guardan los estudiantes y los cursos.
        studentRepository.saveAll(List.of(student1, student2));

        System.out.println("student1 = " + student1);
        System.out.println("student2 = " + student2);
    }

    @Transactional
    public void manyToManyBidirectionalRemoveFind() {
        Student student1 = studentRepository
                .findOneWithCourses(1L)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));

        Student student2 = studentRepository
                .findOneWithCourses(2L)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));

        Course course1 = courseRepository
                .findById(1L)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));

        Course course2 = courseRepository
                .findById(2L)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));

        Course course3 = courseRepository
                .findById(3L)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));

        // Se asignan los cursos a los estudiantes.
        student1.addCourse(course1).addCourse(course2);
        student2.addCourse(course2).addCourse(course3);

        // Se guardan los estudiantes y los cursos.
        studentRepository.saveAll(List.of(student1, student2));

        System.out.println("student1 = " + student1);
        System.out.println("student2 = " + student2);

        // Se eliminan los cursos de los estudiantes.
        Student student1Db = studentRepository
                .findOneWithCourses(1L)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));

        Course courseDb = courseRepository
                .findById(2L)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));

        student1Db.removeCourse(courseDb);
        studentRepository.save(student1Db);

        System.out.println("student1Db = " + student1Db);
    }

}
