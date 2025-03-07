package com.saec.formtic.service.user;

import com.saec.formtic.controller.user.employee.employeeDTO.EmployeeDTO;
import com.saec.formtic.model.role.Role;
import com.saec.formtic.model.role.RoleName;
import com.saec.formtic.model.user.Employee;
import com.saec.formtic.repository.role.RoleRepository;
import com.saec.formtic.repository.user.EmployeeRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;


@Service
public class EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    RoleRepository roleRepository;

    @Transactional
    public String createEmployee(@RequestBody EmployeeDTO employeeDTO) throws Exception {
        Optional<Role> roleOptional = roleRepository.findFirstByName(RoleName.EMPLOYEE);
        if(roleOptional.isEmpty())
            throw new Exception("Role not found");
        Role employeeRole = roleOptional.get();
        Optional<Employee> employeeOptionalNumber = employeeRepository.findFirstByEmployeeNumber(employeeDTO.getEmployeeNumber());
        if(employeeOptionalNumber.isPresent())
            throw new Exception("Employee number already in use");
        Optional<Employee> employeeOptional = employeeRepository.findFirstByUsername(employeeDTO.getUsername());
        if(employeeOptional.isPresent())
            throw new Exception("Employee username already in use");
        Employee employee = employeeDTO.createEmployee(employeeRole);
        employeeRepository.save(employee);
        return "Employee created";
    }
}
