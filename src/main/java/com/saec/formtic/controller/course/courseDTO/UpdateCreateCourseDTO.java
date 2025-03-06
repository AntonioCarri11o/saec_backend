package com.saec.formtic.controller.course.courseDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateCreateCourseDTO {
    @NotBlank(message = "The course name cannot be empty")
    @Size(max = 128, message = "The course name cannot exceed 128 characters")
    private String name;

    @NotBlank(message = "The course description cannot be empty")
    @Size(max = 255, message = "The description cannot exceed 255 characters")
    private String description;

    private UUID teacherId;
}
