package com.saec.formtic.controller.user.employee;

import com.saec.formtic.controller.user.employee.employeeDTO.EmployeeDTO;
import com.saec.formtic.model.user.Employee;
import com.saec.formtic.service.user.EmployeeService;
import com.saec.formtic.utils.CustomResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/employee")
@CrossOrigin({"*"})
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    @PostMapping("/create")
    public ResponseEntity<CustomResponse<Employee>> createEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) {
        return employeeService.createEmployee(employeeDTO);
    }

}
