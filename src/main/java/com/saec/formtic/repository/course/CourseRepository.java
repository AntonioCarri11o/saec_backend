package com.saec.formtic.repository.course;

import com.saec.formtic.model.course.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CourseRepository extends JpaRepository<Course, UUID> {
    //Devuelve una lista paginada de cursos filtrados por nombre
    Page<Course> findByNameContainingIgnoreCase(String name, Pageable pageable);
    //Indica si existe un curso con con un nombre especificado
    boolean existsByNameIgnoreCase(String name);
}
