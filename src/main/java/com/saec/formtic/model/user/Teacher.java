package com.saec.formtic.model.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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
}
