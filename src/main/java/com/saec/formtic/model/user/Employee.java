package com.saec.formtic.model.user;

import com.saec.formtic.model.department.Department;
import com.saec.formtic.model.job.Job;
import com.saec.formtic.model.role.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Employee extends UserInfo {
    @Column(name = "employee_number", length = 16, nullable = false, unique = true)
    private String employeeNumber;
    @ManyToMany
    private Set<Department> departments;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_job")
    private Job job;

    public Employee(String username, String password, String name, Date hireDate, String employeeNumber, Role role) {
        super(username, password, name, hireDate, role);
        this.employeeNumber = employeeNumber;
    }

    public Employee(String username, String password, String name, String employeeNumber, Role role) {
        super(username, password, name, role);
        this.employeeNumber = employeeNumber;
    }
}
