package com.saec.formtic.service.user;

import com.saec.formtic.controller.user.employee.employeeDTO.EmployeeDTO;
import com.saec.formtic.model.role.Role;
import com.saec.formtic.model.role.RoleName;
import com.saec.formtic.model.user.Employee;
import com.saec.formtic.repository.role.RoleRepository;
import com.saec.formtic.repository.user.EmployeeRepository;
import com.saec.formtic.utils.CustomResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Optional;


@Service
public class EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    RoleRepository roleRepository;

    @Transactional
    public ResponseEntity<CustomResponse<Employee>> createEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Optional<Role> roleOptional = roleRepository.findFirstByName(RoleName.EMPLOYEE);
        if(roleOptional.isEmpty())
            return new ResponseEntity<>(new CustomResponse<>(404, "Not role found", true, null), HttpStatus.NOT_FOUND);
        Role employeeRole = roleOptional.get();
        Optional<Employee> employeeOptionalNumber = employeeRepository.findFirstByEmployeeNumber(employeeDTO.getEmployeeNumber());
        if(employeeOptionalNumber.isPresent())
            return new ResponseEntity<>(new CustomResponse<>(409, "The employee number already exists", true, null), HttpStatus.CONFLICT);
        Optional<Employee> employeeOptional = employeeRepository.findFirstByUsername(employeeDTO.getUsername());
        if(employeeOptional.isPresent())
            return new ResponseEntity<>(new CustomResponse<>(409, "The employee username is already in use", true, null), HttpStatus.CONFLICT);
        Employee employee = employeeDTO.createEmployee(employeeRole);
        Employee savedEmployee = employeeRepository.save(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(new CustomResponse(201, "The employee was created", false, savedEmployee));
    }
}
