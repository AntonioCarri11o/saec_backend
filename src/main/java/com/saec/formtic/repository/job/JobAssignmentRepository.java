package com.saec.formtic.repository.job;

import com.saec.formtic.model.department.Department;
import com.saec.formtic.model.job.Job;
import com.saec.formtic.model.job.JobAssignment;
import com.saec.formtic.model.user.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JobAssignmentRepository extends JpaRepository<JobAssignment, UUID> {

    boolean existsByJobAndDepartmentAndEmployee(Job job, Department department, Employee employee);

    boolean existsByDepartmentAndEmployee(Department department, Employee employee);
}
