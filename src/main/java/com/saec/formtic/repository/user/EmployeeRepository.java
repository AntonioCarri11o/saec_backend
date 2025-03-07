package com.saec.formtic.repository.user;

import com.saec.formtic.model.user.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {
    Optional<Employee> findFirstByUsername(String username);
    Optional<Employee> findFirstByEmployeeNumber(String employeeNumber);
}
