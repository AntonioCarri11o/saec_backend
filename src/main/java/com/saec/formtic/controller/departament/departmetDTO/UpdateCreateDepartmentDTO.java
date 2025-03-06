package com.saec.formtic.controller.departament.departmetDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateCreateDepartmentDTO {
    @NotBlank (message = "The department name cannot be empty")
    @Size(max = 128, message = "The department name cannot exceed 128 characters")
    private String name;
}
