package com.saec.formtic.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.saec.formtic.model.role.Role;
import com.saec.formtic.model.status.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_info")
@Inheritance(strategy = InheritanceType.JOINED)
public class UserInfo {
    public interface BasicView {}
    public interface ListView extends BasicView{}
    public interface ProfileView extends ListView {}

    @Id
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @GeneratedValue(generator = "UUID")
    @Column(name = "id_user_info")
    @JsonView(BasicView.class)
    private UUID idUserInfo;

    @JsonView(ProfileView.class)
    @Column(name = "username", length = 32, nullable = false, unique = true)
    private String username;

    @JsonIgnore
    @Column(name = "password", length = 255, nullable = false)
    private String password;

    @JsonView(ListView.class)
    @Column(name = "fullname", length = 146, nullable = false)
    private String fullname;

    @Column(name = "hire_date", nullable = true)
    private Date hireDate;

    @JsonIgnore
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


    UserInfo(UUID idUserInfo){
        this.idUserInfo = idUserInfo;
    }

    public UserInfo(String username, String password, String name, Date hireDate, Role role) {
        this.username = username;
        this.password = password;
        this.fullname = name;
        this.hireDate = hireDate;
        this.role = role;
    }

    public UserInfo(String username, String password, String name, Role role) {
        this.username = username;
        this.password = password;
        this.fullname = name;
        this.role = role;
    }
}
