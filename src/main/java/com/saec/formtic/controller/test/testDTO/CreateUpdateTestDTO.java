package com.saec.formtic.controller.test.testDTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.saec.formtic.model.course.Course;
import com.saec.formtic.model.status.Status;
import com.saec.formtic.model.test.MasterTest;
import com.saec.formtic.validation.annotation.GlobalText;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUpdateTestDTO {
    @Size(max = 255, message = "The test name must not exceed 255 characters")
    private String id;

    @GlobalText(mandatory = true, field = "version")
    @Size(max = 16, message = "The test version must not exceeed 16 characteers")
    private String version;

    @GlobalText (mandatory = true, field = "name")
    @Size(max = 255, message = "The test name must not exceed 255 characters")
    private String name;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date expirationDate;

    @NotBlank(message = "The courseId is mandatory")
    private String courseId;

    public CreateUpdateTestDTO(String name, Date expirationDate, String courseId) {
        this.name = name;
        this.expirationDate = expirationDate;
        this.courseId = courseId;
    }

    public MasterTest createMasterTest(Status status, Course course) {
        MasterTest masterTest = new MasterTest();
        masterTest.setVersion(this.version);
        masterTest.setName(this.name);
        masterTest.setExpirationDate(this.expirationDate);
        masterTest.setStatus(status);
        masterTest.setCourse(course);
        return masterTest;
    }

}
