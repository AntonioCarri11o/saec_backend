package com.saec.formtic.controller.job.jobDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateCreateJobDTO {
    @NotBlank(message = "Job name is mandatory")
    @Size(max = 128, message = "Job name must not exceed 128 characters")
    private String name;
    @NotBlank(message = "Job description is mandatory")
    @Size(max = 256, message = "Job description must not exceed 256 characters")
    private String description;
}
