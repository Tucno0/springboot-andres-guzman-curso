package org.tucno.springboot.jpa.repositories;

import org.springframework.data.repository.CrudRepository;
import org.tucno.springboot.jpa.entities.Course;

public interface CourseRepository extends CrudRepository<Course, Long> {
}
