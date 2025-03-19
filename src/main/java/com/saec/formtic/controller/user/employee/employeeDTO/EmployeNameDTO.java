package com.saec.formtic.controller.user.employee.employeeDTO;

import com.saec.formtic.validation.annotation.GlobalText;
import com.saec.formtic.validation.validator.enums.TextPattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EmployeNameDTO {
    @GlobalText(textPattern = TextPattern.ONLY_LETTERS, field = "name")
    @Size(max = 146)
    private String name;
}
