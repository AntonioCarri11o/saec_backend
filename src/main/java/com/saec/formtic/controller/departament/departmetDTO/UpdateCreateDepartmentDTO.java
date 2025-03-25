package com.saec.formtic.controller.departament.departmetDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateCreateDepartmentDTO {
    @NotBlank (message = "Department name is mandatory")
    @Size(max = 128, message = "Department name must not exceed 128 characters")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ0-9 ]+$", message = "Department name must not contain special characters")
    private String name;
}
