package com.saec.formtic.controller.course.courseDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateCreateCourseDTO {
    @NotBlank(message = "Course name is mandatory")
    @Size(max = 128, message = "The course must not exceed 128 characters")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ0-9 ]+$", message = "Course name must not contain special characters")
    private String name;

    @NotBlank(message = "Course description is mandatory")
    @Size(max = 255, message = "The description must not exceed 128 characters")
    private String description;

    private String teacherId;
}
