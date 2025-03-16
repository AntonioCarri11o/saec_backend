package com.saec.formtic.controller.user.employee.employeeDTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.saec.formtic.model.role.Role;
import com.saec.formtic.model.user.Employee;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {
    @NotBlank(message = "Employee number is mandatory")
    @Size(max = 16, message = "The employee number must not exceed 16 characters")
    private String employeeNumber;

    @NotBlank(message = "Username is mandatory")
    @Size(max = 32, message = "The username must not exceed 32 characters")
    String username;

    @NotBlank(message = "Password is mandatory")
    String password;

    @NotBlank(message = "Name is mandatory")
    @Size(max = 48, message = "The name must not exceed 48 characters")
    String name;

    @JsonFormat(pattern = "yyyy-MM-dd")
    Date hireDate;

    public Employee createEmployee(Role role) {
        if(hireDate != null)
            return new Employee(
                    username,
                    password,
                    name,
                    hireDate,
                    employeeNumber,
                    role
            );
        return new Employee(
                username,
                password,
                name,
                employeeNumber,
                role
        );
    }
}
