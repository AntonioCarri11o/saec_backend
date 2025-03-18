package com.saec.formtic.controller.job.jobDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateCreateJobAssignmentDTO {
    @NotBlank(message = "Job id is mandatory")
    String jobID;
    @NotBlank(message = "Department id is mandatory")
    String departmentID;
    @NotBlank(message = "Employee id is mandatory")
    String employeeID;
}
