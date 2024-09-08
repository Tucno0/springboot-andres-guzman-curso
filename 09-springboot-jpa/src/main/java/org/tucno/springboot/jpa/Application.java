package org.tucno.springboot.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;
import org.tucno.springboot.jpa.dto.PersonDto;
import org.tucno.springboot.jpa.entities.Person;
import org.tucno.springboot.jpa.repositories.PersonRepository;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@SpringBootApplication
// La interfaz CommandLineRunner se utiliza para indicar que un bean debe ejecutarse cuando se complete la inicialización del contexto de la aplicación.
public class Application implements CommandLineRunner {
    @Autowired
    private PersonRepository personRepository;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    // Este método se ejecutará cuando se complete la inicialización del contexto de la aplicación
    @Override
    public void run(String... args) throws Exception {
//        findOne();
//        list();
//        create();
//        update();
//        delete();
//        delete2();
//        personalizedQueries();
//        personalizedQueries2();
//        personalizedQueriesDistinct();
//        personalizedQueriesAgregrationFunctions();
//        personalizedQueriesBetweenOrderBy();
//        funcionesDeAgregacion();
        subqueries();
    }

    // readOnly = true indica que el método no realizará operaciones de escritura en la base de datos.
    @Transactional(readOnly = true)
    public void findOne() {
//        Person person = null;
//        Optional<Person> optionalPerson = personRepository.findById(1L);
//        if (optionalPerson.isPresent()) {
//            person = optionalPerson.get();
//        }
//        System.out.println(person);

        // Forma más corta de obtener un objeto de tipo Person
        personRepository.findById(1L).ifPresent( person -> System.out.println("findById: " + person));
        personRepository.findOne(1L).ifPresent(person -> System.out.println("findOne: " + person));
        personRepository.findOneByName("Andres").ifPresent(person -> System.out.println("findOneByName: " + person));
        personRepository.findOneLikeName("And").ifPresent(person -> System.out.println("findOneLikeName: " + person));

        personRepository.findByName("Andres").ifPresent(person -> System.out.println("findByName: " + person));
        personRepository.findByNameContaining("Andres").ifPresent(person -> System.out.println("findByNameContaining: " + person));
    }

    // readOnly = true indica que el método no realizará operaciones de escritura en la base de datos.
    @Transactional(readOnly = true)
    public void list() {
        // findAll() es un método proporcionado por la interfaz CrudRepository que se extiende en la interfaz PersonRepository.
//        List<Person> persons = (List<Person>) personRepository.findAll();

        /**
         * findByProgrammingLanguage() es un método personalizado definido en la interfaz PersonRepository.
         * Este método buscará todas las personas con el lenguaje de programación especificado.
         * Spring Data JPA generará una consulta JPQL que buscará todas las personas con el lenguaje de programación especificado.
         * Tiene que cumplir con la convención de nombres findBy<Nombre de la propiedad> para que Spring Data JPA pueda generar la consulta.
         */
//        List<Person> persons = (List<Person>) personRepository.findByProgrammingLanguage("Java");

        /**
         * findByProgrammingLanguageJPQL() es un método personalizado definido en la interfaz PersonRepository.
         * Este método utiliza una consulta JPQL personalizada para buscar todas las personas con el lenguaje de programación especificado.
         * La anotación @Query se utiliza para definir una consulta JPQL personalizada.
         * Las consultas JPQL se definen utilizando la sintaxis de JPQL, que es similar a SQL pero utiliza entidades y propiedades de entidades en lugar de tablas y columnas.
         */
//        List<Person> persons = (List<Person>) personRepository.findByProgrammingLanguageJPQL("Java");
        List<Person> persons = (List<Person>) personRepository.findByProgrammingLanguageAndNameJPQL("Java", "Andres");
        persons.forEach(System.out::println);

        List<Object[]> personData = personRepository.getPersonData();
        personData.forEach(person -> {
            String name = (String) person[0];
            String programmingLanguage = (String) person[1];
            System.out.println("Name: " + name + ", Programming Language: " + programmingLanguage);
        });
    }

    // La anotación @Transactional se utiliza para indicar que el método debe ejecutarse dentro de una transacción.
    // Si se produce una excepción, la transacción se revertirá.
    // Esto es útil cuando se realizan operaciones de lectura y escritura en la base de datos.
    @Transactional
    public void create() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter name: ");
        String name = scanner.nextLine();

        System.out.print("Enter lastname: ");
        String lastname = scanner.nextLine();

        System.out.print("Enter programming language: ");
        String programmingLanguage = scanner.nextLine();

        Person person = new Person(null, name, lastname, programmingLanguage);
        Person createdPerson = personRepository.save(person);

        System.out.println("Created person: " + createdPerson);

        personRepository.findById(createdPerson.getId()).ifPresent(p -> System.out.println("findById: " + p));
    }

    @Transactional
    public void update() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter id: ");
        Long id = scanner.nextLong();

        Optional<Person> optionalPerson = personRepository.findById(id);

        optionalPerson.ifPresent(person -> {
            System.out.println("Person found: " + person);
            System.out.print("Enter new programming language: ");
            String programmingLanguage = scanner.next();
            person.setProgrammingLanguage(programmingLanguage);
            Person personDb = personRepository.save(person);
            System.out.println("Updated person: " + personDb);
        });

        personRepository.findById(id).ifPresent(person -> System.out.println("findById: " + person ));
        scanner.close();
    }

    @Transactional
    public void delete() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter id: ");
        Long id = scanner.nextLong();

        Optional<Person> optionalPerson = personRepository.findById(id);

        optionalPerson.ifPresentOrElse(person -> {
            System.out.println("Person found: " + person);
            personRepository.delete(person);
            System.out.println("Person deleted");
        }, () -> System.out.println("Person not found"));

        scanner.close();
    }

    @Transactional
    public void delete2() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter id: ");
        Long id = scanner.nextLong();

        personRepository.deleteById(id);

        personRepository.findAll().forEach(System.out::println);
    }

    @Transactional
    public void personalizedQueries() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter id: ");
        Long id = scanner.nextLong();

        String name = personRepository.getNameById(id);
        System.out.println("Name: " + name);

        Long id2 = personRepository.getIdById(id);
        System.out.println("Id: " + id2);

        String fullName = personRepository.getFullNameById(id);
        System.out.println("Full name: " + fullName);

        List<Object[]> fullPersonData = personRepository.getFullPersonData();
        fullPersonData.forEach(person -> {
            Long id3 = (Long) person[0];
            String name2 = (String) person[1];
            String lastname = (String) person[2];
            String programmingLanguage = (String) person[3];
            System.out.println("Id: " + id3 + ", Name: " + name2 + ", Lastname: " + lastname + ", Programming Language: " + programmingLanguage);
        });

        Optional<Object> fullPersonDataById = personRepository.getFullPersonDataById(id);
        fullPersonDataById.ifPresent(person -> {
            Object[] personArray = (Object[]) person;
            Long id3 = (Long) personArray[0];
            String name2 = (String) personArray[1];
            String lastname = (String) personArray[2];
            String programmingLanguage = (String) personArray[3];
            System.out.println("Id: " + id3 + ", Name: " + name2 + ", Lastname: " + lastname + ", Programming Language: " + programmingLanguage);
        });

        scanner.close();
    }

    @Transactional
    public void personalizedQueries2() {
        System.out.println("============== Consulta por objeto persona y lenguaje de programación ==============");
        List<Object[]> allMixPerson = personRepository.findAllMixPerson();
        allMixPerson.forEach(mix -> {
            Person p = (Person) mix[0];
            String programmingLanguage = (String) mix[1];
            System.out.println("Person: " + p + ", Programming Language: " + programmingLanguage);
        });

        System.out.println("\n============== Consulta que puebla y devuelve un objeto entity de una insatancia personalizada ==============");
        List<Person> allClassPerson = personRepository.findAllClassPerson();
        allClassPerson.forEach(System.out::println);

        System.out.println("\n============== Consulta que puebla y devuelve un objeto dto de una clase personalizada ==============");
        List<PersonDto> allDtoPerson = personRepository.findAllClassPersonDto();
        allDtoPerson.forEach(System.out::println);
    }

    @Transactional(readOnly = true)
    public void personalizedQueriesDistinct() {
        System.out.println("============== Consulta que devuelve nombres ==============");
        List<String> allNames = personRepository.findAllNames();
        allNames.forEach(System.out::println);

        System.out.println("\n============== Consulta que devuelve nombres distintos ==============");
        List<String> distinctNames = personRepository.findDistinctNames();
        distinctNames.forEach(System.out::println);

        System.out.println("\n============== Consulta que devuelve lenguajes de programación distintos ==============");
        List<String> distinctProgrammingLanguages = personRepository.findDistinctProgrammingLanguages();
        distinctProgrammingLanguages.forEach(System.out::println);
    }

    @Transactional(readOnly = true)
    public void personalizedQueriesAgregrationFunctions() {
        System.out.println("============== Consulta que devuelve el número de personas ==============");
        long count = personRepository.count();
        System.out.println("Count: " + count);

        System.out.println("\n============== Consulta que devuelve la cantidad de lengujes de programación distintos ==============");
        Long countDistinctProgrammingLanguages = personRepository.countDistinctProgrammingLanguages();
        System.out.println("Count distinct programming languages: " + countDistinctProgrammingLanguages);

        System.out.println("\n============== Consulta que devuelve nombres concatenados ==============");
        List<String> fullNames = personRepository.concatFullNames();
        fullNames.forEach(System.out::println);

        System.out.println("\n============== Consulta que devuelve nombres concatenados en mayúsculas ==============");
        List<String> upperFullNames = personRepository.concatUpperFullNames();
        upperFullNames.forEach(System.out::println);

        System.out.println("\n============== Consulta que devuelve nombres concatenados en minúsculas ==============");
        List<String> lowerFullNames = personRepository.concatLowerFullNames();
        lowerFullNames.forEach(System.out::println);

        System.out.println("\n============== Consulta que devuelve datos de personas con casos mixtos ==============");
        List<Object[]> allPersonDataListCase = personRepository.findAllPersonDataListCase();
        allPersonDataListCase.forEach(person -> {
            Long id = (Long) person[0];
            String name = (String) person[1];
            String lastname = (String) person[2];
            String programmingLanguage = (String) person[3];
            System.out.println("Id: " + id + ", Name: " + name + ", Lastname: " + lastname + ", Programming Language: " + programmingLanguage);
        });
    }

    @Transactional( readOnly = true )
    public void personalizedQueriesBetweenOrderBy() {
        System.out.println("============================= Consultas por rangos =============================");
        List<Person> findAllBetween = personRepository.findAllBetween(2L, 5L);
        findAllBetween.forEach(System.out::println);

        System.out.println("\n============================= Consultas por rangos de nombres =============================");
        List<Person> findAllBetweenNames = personRepository.findAllBetweenNames("J", "Q");
        findAllBetweenNames.forEach(System.out::println);

        System.out.println("\n============================= Consultas order by =============================");
        List<Person> findAllBetweenOrderByIdDesc = personRepository.findAllBetweenOrderByIdDesc(2L, 5L);
        findAllBetweenOrderByIdDesc.forEach(System.out::println);
    }

    @Transactional( readOnly = true )
    public  void funcionesDeAgregacion() {
        System.out.println("\n============================= Count total de personas =============================");
        Long count = personRepository.getTotalPerson();
        System.out.println("count = " + count);

        System.out.println("\n============================= Minimo ID =============================");
        Long minId = personRepository.getMinId();
        System.out.println("getMinId = " + minId);

        System.out.println("\n============================= Max ID =============================");
        Long maxId = personRepository.getMaxId();
        System.out.println("getMaxId = " + maxId);

        System.out.println("\n============================= LENGTH =============================");
        List<Object[]> lengths = personRepository.getNameLength();
        lengths.forEach(length -> {
            String name = (String) length[0];
            int lengthName = (int) length[1];
            System.out.println("name = " + name + ", length = " + lengthName);
        });

        System.out.println("\n============================= Obtener el nombre mas corto =============================");
        Long shortestName = personRepository.getMinNameLength();
        System.out.println("shortestName = " + shortestName);

        System.out.println("\n============================= Obtener el nombre mas largo =============================");
        Long longestName = personRepository.getMaxNameLength();
        System.out.println("longestName = " + longestName);

        System.out.println("\n============================= Promedio de longitud de nombres =============================");
        Double avgNameLength = personRepository.getAvgNameLength();
        System.out.println("avgNameLength = " + avgNameLength);

        System.out.println("\n============================= Suma de longitud de nombres =============================");
        Long sumNameLength = personRepository.getSumNameLength();
        System.out.println("sumNameLength = " + sumNameLength);

        System.out.println("\n============================= Todas las funciones de agregación =============================");
        List<Object[]> allAggregationFunctions = personRepository.getPersonAggregations();
        allAggregationFunctions.forEach(aggregation -> {
            Long count2 = (Long) aggregation[0];
            Long minId2 = (Long) aggregation[1];
            Long maxId2 = (Long) aggregation[2];
            Double avgId = (Double) aggregation[3];
            Long sumId = (Long) aggregation[4];
            System.out.println("count = " + count2 + ", minId = " + minId2 + ", maxId = " + maxId2 + ", avgId = " + avgId + ", sumId = " + sumId);
        });
    }

    @Transactional( readOnly = true )
    public void subqueries() {
        System.out.println("\n============================= findMaxIdPerson =============================");
        Person maxIdPerson = personRepository.findMaxIdPerson();
        System.out.println("maxIdPerson = " + maxIdPerson);

        System.out.println("\n============================= findMinIdPerson =============================");
        Person minIdPerson = personRepository.findMinIdPerson();
        System.out.println("minIdPerson = " + minIdPerson);

        System.out.println("\n============================= getShorterName =============================");
        List<Object[]> shorterName = personRepository.getShorterName();
        shorterName.forEach(name -> {
            String name2 = (String) name[0];
            int length = (int) name[1];
            System.out.println("name = " + name2 + ", length = " + length);
        });

        System.out.println("\n============================= findByIdIn =============================");
        List<Person> persons = personRepository.findByIdIn(List.of(1L, 3L, 5L));
        persons.forEach(System.out::println);
    }
}
