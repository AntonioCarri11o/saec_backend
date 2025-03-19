package com.saec.formtic.repository.user;

import com.saec.formtic.model.status.Status;
import com.saec.formtic.model.user.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID> {
    Optional<Employee> findFirstByUsername(String username);
    List<Employee> findByFullnameContainingIgnoreCase(String name);
    Optional<Employee> findFirstByEmployeeNumber(String employeeNumber);
    List<Employee> findByStatus(Status status);
}
