package com.saec.formtic.model.course;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CourseRepository extends JpaRepository<Course, UUID> {


    Page<Course> findByNameContainingIgnoreCase(String name, Pageable pageable);

    boolean existsByName(String name);
}
