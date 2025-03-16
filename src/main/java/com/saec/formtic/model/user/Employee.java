package com.saec.formtic.model.user;

import com.saec.formtic.model.role.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Employee extends UserInfo {
    @Column(name = "employee_number", length = 16, nullable = false, unique = true)
    private String employeeNumber;

    public Employee(String username, String password, String name, Date hireDate, String employeeNumber, Role role) {
        super(username, password, name, hireDate, role);
        this.employeeNumber = employeeNumber;
    }

    public Employee(String username, String password, String name, String employeeNumber, Role role) {
        super(username, password, name, role);
        this.employeeNumber = employeeNumber;
    }
}
