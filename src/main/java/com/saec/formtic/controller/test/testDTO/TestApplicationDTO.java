package com.saec.formtic.controller.test.testDTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestApplicationDTO {

    @NotNull(message = "The start course date is mandatory")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startCourseDate;

    @NotNull(message = "The deadline date is mandatory")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date deadlineDate;

    @NotBlank(message = "The master test id is mandatory")
    @Size(max = 255, message = "The master test id must not exceed 255 characters")
    private String masterTestId;

    @NotNull(message = "The employee list most not be empty")
    private List<String> employeesIDs;

}
