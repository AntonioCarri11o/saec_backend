package com.saec.formtic.model.user;

import com.saec.formtic.model.role.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Teacher extends UserInfo {
    @Column(name = "email", length = 32, nullable = false, unique = true)
    private String email;

    @Column(name = "sign_image_src_url", length = 255, nullable = true)
    private String signImageSrcUrl;

    public Teacher(String username, String password, String email, String name, String signImageSrcUrl, Role role) {
        super(username, password, name, role);
        this.email = email;
        this.signImageSrcUrl = signImageSrcUrl;
    }
}
