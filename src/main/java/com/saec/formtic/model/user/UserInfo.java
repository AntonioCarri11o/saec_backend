package com.saec.formtic.model.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.saec.formtic.model.role.Role;
import com.saec.formtic.model.status.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@Entity
@Table(name = "user_info")
@Inheritance(strategy = InheritanceType.JOINED)
public class UserInfo {
    @Id
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @GeneratedValue(generator = "UUID")
    @Column(name = "id_user_info")
    private UUID idUserInfo;

    @Column(name = "username", length = 32, nullable = false, unique = true)
    private String username;

    @Column(name = "password", length = 255, nullable = false)
    private String password;

    @Column(name = "name", length = 48, nullable = false)
    private String name;

    @Column(name = "lastname", length = 48, nullable = false)
    private String lastname;

    @Column(name = "surname", length = 48, nullable = true)
    private String surname;

    @Column(name = "hire_date", nullable = true)
    private Date hireDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_role")
    private Role role;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_status")
    private Status status;

    @PrePersist
    private void generateUUID() {
        if(idUserInfo == null) {
            idUserInfo = UUID.randomUUID();
        }
    }

    public UserInfo() {}

    public UserInfo(String username, String password, String name, String lastname, String surname, Date hireDate, Role role) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.lastname = lastname;
        this.surname = surname;
        this.hireDate = hireDate;
        this.role = role;
    }

    public UserInfo(String username, String password, String name, String lastname, String surname, Role role) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.lastname = lastname;
        this.surname = surname;
        this.role = role;
    }
}
