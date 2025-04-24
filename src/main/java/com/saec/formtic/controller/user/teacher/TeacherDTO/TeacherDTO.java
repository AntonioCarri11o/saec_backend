package com.saec.formtic.controller.user.teacher.TeacherDTO;

import com.saec.formtic.model.role.Role;
import com.saec.formtic.model.user.Teacher;
import com.saec.formtic.utils.CustomResponse;
import com.saec.formtic.utils.Utils;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherDTO {
    @NotBlank(message = "Teacher email is mandatory")
    @Size(max = 32, message = "The email must not exceed 32 characters")
    String email;

    @NotBlank(message = "Username is mandatory")
    @Size(max = 32, message = "The username must not exceed 32 characters")
    String username;

    @NotBlank(message = "Password is mandatory")
    String password;

    @NotBlank(message = "Name is mandatory")
    @Size(max = 48, message = "The name must not exceed 48 characters")
    String name;

    @Size(max = 255, message = "The image url must not exceed 255 characters")
    String imageUrl;
    public Teacher createTeacher(Role role) {
        return new Teacher(username, password, email, name, imageUrl, role);
    }
}
