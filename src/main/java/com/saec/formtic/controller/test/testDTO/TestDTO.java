package com.saec.formtic.controller.test.testDTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.saec.formtic.model.course.Course;
import com.saec.formtic.model.status.Status;
import com.saec.formtic.model.test.MasterTest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestDTO {
    @NotBlank(message = "The version name is mandatory")
    @Size(max = 16, message = "The version name must not exceed 16 characters")
    private String version;

    @NotBlank
    @Size(max = 128, message = "The description must not exceed 128 characters")
    private String name;

    @NotNull(message = "The expiration date is mandatory")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date expirationDate;

    @NotBlank(message = "Course id is mandatory")
    @Size(max = 255, message = "Course id is not valid")
    private String courseId;

    public MasterTest createMasterTest(Status status, Course course) {
        return new MasterTest(
                this.version,
                this.name,
                this.expirationDate,
                status,
                course
        );
    }

}
