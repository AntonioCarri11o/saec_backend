package com.saec.formtic.repository.user;

import com.saec.formtic.model.role.Role;
import com.saec.formtic.model.user.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, UUID> {
    Optional<UserInfo> findUserInfoByUsername(String username);
    int countByRole(Role role);
}
