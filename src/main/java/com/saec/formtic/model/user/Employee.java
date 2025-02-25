package com.saec.formtic.model.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Employee extends UserInfo {
    @Column(name = "employee_number", length = 16, nullable = false, unique = true)
    private String employeeNumber;
}
