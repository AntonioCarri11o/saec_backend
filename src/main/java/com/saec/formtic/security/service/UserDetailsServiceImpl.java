package com.saec.formtic.security.service;

import com.saec.formtic.controller.auth.authDTO.LoginDTO;
import com.saec.formtic.model.user.UserInfo;
import com.saec.formtic.repository.user.UserInfoRepository;
import com.saec.formtic.utils.CustomResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
    private final UserInfoRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    @Autowired
    public UserDetailsServiceImpl(UserInfoRepository repository, PasswordEncoder passwordEncoder,
                                  JwtService jwtService)  {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo userInfo = repository.findUserInfoByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(userInfo.getRole().getName())));

        return new User(userInfo.getUsername(),
                userInfo.getPassword(),
                true,
                true,
                true,
                true,
                authorityList);
    }

    public CustomResponse<?> login(LoginDTO request) {
        try{
            Authentication authentication = this.authenticate(request.getUsername(), request.getPassword());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtService.createToken(authentication);
            return new CustomResponse<>(
                    HttpStatus.OK.value(),
                    "Authenticated user successfully",
                    false,
                    token
            );
        } catch (IllegalArgumentException | IllegalStateException ex) {
            logger.error("loginRequest - Validation or Conflict error: {}", ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            logger.error("loginRequest - Unexpected error: {}", ex.getMessage());
            throw new RuntimeException("Error during request.",ex);
        }
    }

    private Authentication authenticate (String username, String password) {
        UserDetails userDetails = this.loadUserByUsername(username);

        if (userDetails == null) {
            throw new BadCredentialsException("Invalid credentials");
        }

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid credentials");
        }

        return new UsernamePasswordAuthenticationToken(username, password, userDetails.getAuthorities());
    }
}
