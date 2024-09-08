package org.tucno.springboot.jpa.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.tucno.springboot.jpa.dto.PersonDto;
import org.tucno.springboot.jpa.entities.Person;

import java.util.List;
import java.util.Optional;

// Spring Data JPA proporciona una interfaz CrudRepository que se puede extender para obtener métodos CRUD básicos para una entidad.
public interface PersonRepository extends CrudRepository<Person, Long> {
    /**
     * Spring Data JPA proporciona una convención de nombres para definir consultas personalizadas sin tener que escribir una consulta JPQL.
     * En este caso, Spring Data JPA generará una consulta JPQL que buscará todas las personas con el lenguaje de programación especificado.
     * Tiene que cumplir con la convención de nombres findBy<Nombre de la propiedad> para que Spring Data JPA pueda generar la consulta.
     */

    // CONSULTAS PREDEFINIDAS POR SPRING DATA JPA
    List<Person> findByProgrammingLanguage(String programmingLanguage);

    Optional<Person> findByName(String name);

    Optional<Person> findByNameContaining(String name);

    List<Person> findByIdBetween(Long id1, Long id2);

    List<Person> findByNameBetween(String name1, String name2);

    List<Person> findByNameBetweenOrderByIdDesc(String name, String name2);

    List<Person> findByNameBetweenOrderByNameDesc(String name1, String name2);

    List<Person> findAllByOrderByNameDescLastnameAsc();

    /**
     * Método que utiliza una consulta JPQL personalizada para buscar todas las personas con el lenguaje de programación especificado.
     * La anotación @Query se utiliza para definir una consulta JPQL personalizada.
     * Las consultas JPQL se definen utilizando la sintaxis de JPQL, que es similar a SQL pero utiliza entidades y propiedades de entidades en lugar de tablas y columnas.
     */

    // CONSULTAR VARIAS FILAS
    @Query("SELECT p FROM Person p WHERE p.programmingLanguage = ?1")
    List<Person> findByProgrammingLanguageJPQL(String programmingLanguage);

    @Query("SELECT p FROM Person p WHERE p.programmingLanguage = ?1 AND p.name = ?2")
    List<Person> findByProgrammingLanguageAndNameJPQL(String programmingLanguage, String name);

    @Query(value = "SELECT p.name, p.programmingLanguage FROM Person p")
    List<Object[]> getPersonData();

    @Query(value = "SELECT p.name, p.programmingLanguage FROM Person p WHERE p.name = ?1")
    List<Object[]> getPersonData(String name);

    @Query(value = "SELECT p.name, p.programmingLanguage FROM Person p WHERE p.programmingLanguage = ?1 AND p.name = ?2")
    List<Object[]> getPersonData(String programmingLanguage, String name);


    // CONSULTAR UNA SOLA FILA
    @Query("SELECT p FROM Person p WHERE p.id = ?1")
    Optional<Person> findOne(Long id);

    @Query("SELECT p FROM Person p WHERE p.name = ?1")
    Optional<Person> findOneByName(String name);

    @Query("SELECT p FROM Person p WHERE p.name LIKE %?1%")
    Optional<Person> findOneLikeName(String name);


    // CONSULTAR COLUMNAS ESPECÍFICAS
    @Query("SELECT p.name FROM Person p WHERE p.id = ?1")
    String getNameById(Long id);

    @Query("SELECT p.id FROM Person p WHERE p.id = ?1")
    Long getIdById(Long id);

    @Query("SELECT concat(p.name, ' ', p.lastname) FROM Person p WHERE p.id = ?1")
    String getFullNameById(Long id);

    @Query("SELECT p.id, p.name, p.lastname, p.programmingLanguage FROM Person p")
    List<Object[]> getFullPersonData();

    @Query("SELECT p.id, p.name, p.lastname, p.programmingLanguage FROM Person p WHERE p.id = ?1")
    Optional<Object> getFullPersonDataById(Long id);

    @Query("SELECT p, p.programmingLanguage FROM Person p")
    List<Object[]> findAllMixPerson();


    // CONSULTAS CON CONSTRUCTORES Y CLASES DTO
    // Para obtener una lista de objetos de una clase específica, se debe utilizar el constructor de la clase en la consulta JPQL.
    @Query("SELECT new Person(p.name, p.lastname) FROM Person p")
    List<Person> findAllClassPerson();

    // Si queremos utilizar una clase DTO, debemos crear un constructor en la clase DTO que coincida con los campos que se devuelven en la consulta JPQL, y también poner el paquete completo de la clase DTO en la consulta JPQL.
    @Query("SELECT new org.tucno.springboot.jpa.dto.PersonDto(p.name, p.lastname) FROM Person p")
    List<PersonDto> findAllClassPersonDto();

    // CONSULTAS CON DISTINCT
    @Query("SELECT p.name FROM Person p")
    List<String> findAllNames();

    @Query("SELECT DISTINCT p.name FROM Person p")
    List<String> findDistinctNames();

    @Query("SELECT DISTINCT p.programmingLanguage FROM Person p")
    List<String> findDistinctProgrammingLanguages();


    // CONSULTAS CON FUNCIONES DE AGREGACIÓN
    @Query("SELECT COUNT(DISTINCT p.programmingLanguage) FROM Person p")
    Long countDistinctProgrammingLanguages();

//    @Query("SELECT CONCAT(p.name, ' ', p.lastname) FROM Person p")
    @Query("SELECT p.name || ' ' || p.lastname FROM Person p")
    List<String> concatFullNames();

    @Query("SELECT UPPER(p.name) || ' ' || UPPER(p.lastname) FROM Person p")
    List<String> concatUpperFullNames();

    @Query("SELECT LOWER( CONCAT(p.name, ' ', p.lastname) ) FROM Person p")
    List<String> concatLowerFullNames();

    @Query("SELECT p.id, UPPER(p.name), LOWER(p.lastname), UPPER(p.programmingLanguage) FROM Person p")
    List<Object[]> findAllPersonDataListCase();


    // BETWEEN, ORDER BY, LIMIT
    @Query("SELECT p FROM Person p WHERE p.id BETWEEN ?1 AND ?2")
    List<Person> findAllBetween(Long id1, Long id2);

    @Query("SELECT p FROM Person p WHERE p.name BETWEEN ?1 AND ?2")
    List<Person> findAllBetweenNames(String name1, String name2);

    @Query("SELECT p FROM Person p WHERE p.id BETWEEN ?1 AND ?2 ORDER BY p.id DESC")
    List<Person> findAllBetweenOrderByIdDesc(Long id1, Long id2);

    @Query("SELECT p FROM Person p ORDER BY p.name DESC, p.lastname ASC")
    List<Person> getAll();

    @Query("SELECT p FROM Person p ORDER BY p.name DESC LIMIT ?1")
    List<Person> getAllLimit(int limit);


    // FUNCIONES DE AGREGACIÓN
    @Query("SELECT COUNT(p) FROM Person p")
    Long getTotalPerson();

    @Query("SELECT MIN(p.id) FROM Person p")
    Long getMinId();

    @Query("SELECT MAX(p.id) FROM Person p")
    Long getMaxId();

    @Query("SELECT p.name, length(p.name) FROM Person p")
    List<Object[]> getNameLength();

    @Query("SELECT MIN( length(p.name) ) FROM Person p")
    Long getMinNameLength();

    @Query("SELECT MAX( length(p.name) ) FROM Person p")
    Long getMaxNameLength();

    @Query("SELECT AVG( length(p.name) ) FROM Person p")
    Double getAvgNameLength();

    @Query("SELECT SUM( length(p.name) ) FROM Person p")
    Long getSumNameLength();

    // todas las funciones de agregacion
    @Query("SELECT COUNT(p), MIN(p.id), MAX(p.id), AVG(p.id), SUM(p.id) FROM Person p")
    List<Object[]> getPersonAggregations();


    // SUBQUERIES
    @Query("SELECT p FROM Person p WHERE p.id = (SELECT MAX(p.id) FROM Person p)")
    Person findMaxIdPerson();

    @Query("SELECT p FROM Person p WHERE p.id = (SELECT MIN(p.id) FROM Person p)")
    Person findMinIdPerson();

    @Query("SELECT p.name, LENGTH(p.name) FROM Person p WHERE LENGTH(p.name) = (SELECT MIN(LENGTH(p.name)) FROM Person p)")
    List<Object[]> getShorterName();


    // WHERE IN
    @Query("SELECT p FROM Person p WHERE p.id IN ?1")
    List<Person> findByIdIn(List<Long> ids);
}
