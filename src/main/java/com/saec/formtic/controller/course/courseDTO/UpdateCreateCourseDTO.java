package com.saec.formtic.controller.course.courseDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateCreateCourseDTO {
    @NotBlank(message = "El nombre del curso no puede estar vacío")
    @Size(max = 128, message = "El nombre del curso no puede tener más de 128 caracteres")
    private String name;

    @NotBlank(message = "La descripción del curso no puede estar vacía")
    @Size(max = 255, message = "La descripción no puede tener más de 255 caracteres")
    private String description;

    private UUID teacherId;
}
