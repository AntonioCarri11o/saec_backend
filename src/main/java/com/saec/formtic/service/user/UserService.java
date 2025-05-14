package com.saec.formtic.service.user;

import com.saec.formtic.repository.user.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    UserInfoRepository userRepository;

    @Autowired
    UserService(UserInfoRepository userRepository) {
        this.userRepository = userRepository;
    }

}
