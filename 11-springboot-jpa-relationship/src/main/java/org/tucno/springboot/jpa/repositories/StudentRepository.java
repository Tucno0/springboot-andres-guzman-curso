package org.tucno.springboot.jpa.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.tucno.springboot.jpa.entities.Student;

import java.util.Optional;

public interface StudentRepository extends CrudRepository<Student, Long> {

    @Query("SELECT s FROM Student s LEFT JOIN FETCH s.courses WHERE s.id = ?1")
    Optional<Student> findOneWithCourses(Long id);
}
