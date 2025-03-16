package com.saec.formtic.repository.job;

import com.saec.formtic.model.department.Department;
import com.saec.formtic.model.job.JobAssignment;
import com.saec.formtic.model.status.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface JobAssignmentRepository extends JpaRepository<JobAssignment, UUID> {
    List<JobAssignment> findJobAssignmentByDepartmentAndEmployeeStatus(Department department, Status status);
}
