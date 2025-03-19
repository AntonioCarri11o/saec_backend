package com.saec.formtic.controller.user.employee.employeeDTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.saec.formtic.validation.annotation.GlobalText;
import com.saec.formtic.validation.validator.enums.TextPattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateEmployeeDTO {

    @GlobalText(textPattern = TextPattern.GLOBAL, field = "username")
    public String username;

    @GlobalText(textPattern = TextPattern.ONLY_LETTERS, field = "name")
    public String name;

    @GlobalText(textPattern = TextPattern.ONLY_LETTERS, field = "lastname")
    public String lastname;

    @GlobalText(textPattern = TextPattern.ONLY_LETTERS, field = "surname")
    public String surname;

    @JsonFormat(pattern = "yyyy-MM-dd")
    public Date hireDate;
}
