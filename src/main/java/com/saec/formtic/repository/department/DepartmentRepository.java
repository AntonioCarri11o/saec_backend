package com.saec.formtic.repository.department;

import com.saec.formtic.model.department.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DepartmentRepository extends JpaRepository<Department, UUID> {


    boolean existsByName(String name);

    Optional<Department> findByName(String name);

    List<Department> getAllByNameContainingIgnoreCase(String name);
}
