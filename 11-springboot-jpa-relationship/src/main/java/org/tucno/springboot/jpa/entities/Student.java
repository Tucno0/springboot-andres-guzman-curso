package org.tucno.springboot.jpa.entities;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String lastName;

    // Dueño de la relación
    @ManyToMany(
            // Para una relación ManyToMany, la tabla intermedia se crea automáticamente
            // En este tipo de relaciones no se puede usar el CascadeType.ALL porque se pueden borrar registros de la tabla intermedia que no se quieren borrar.
            cascade = {
//                    CascadeType.DETACH, // Para que se puedan borrar los cursos de un estudiante
                    CascadeType.MERGE,  // Para que se puedan actualizar los cursos de un estudiante
                    CascadeType.PERSIST, // Para que se puedan insertar los cursos de un estudiante
//                    CascadeType.REFRESH // Para que se puedan refrescar los cursos de un estudiante
            }
    )
    @JoinTable(
            name = "tbl_students_courses",
            joinColumns = @JoinColumn(name = "id_student"),
            inverseJoinColumns = @JoinColumn(name = "id_course"),
            uniqueConstraints = {
                    // Un estudiante no puede estar inscrito en un mismo curso más de una vez
                    @UniqueConstraint(columnNames = {"id_student", "id_course"})
            }
    )
    private Set<Course> courses;

    public Student() {
        this.courses = new HashSet<>();
    }

    public Student(String name, String lastName) {
        this();
        this.name = name;
        this.lastName = lastName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    public Student addCourse(Course course) {
        this.courses.add(course);
        course.getStudents().add(this);
        return this;
    }

    public Student removeCourse(Course course) {
        this.courses.remove(course);
        course.getStudents().remove(this);
        return this;
    }

    @Override
    public String toString() {
        return "{id: " + id + ", name: " + name + ", lastName: " + lastName + ", courses: " + courses + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(id, student.id) && Objects.equals(name, student.name) && Objects.equals(lastName, student.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, lastName);
    }
}
