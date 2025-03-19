package com.saec.formtic.repository.user;

import com.saec.formtic.model.user.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserInfoRepository extends JpaRepository<UserInfo, UUID> {
    Optional<UserInfo> findUserInfoByUsername(String username);
}
