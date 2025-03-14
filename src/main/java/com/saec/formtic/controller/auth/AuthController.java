package com.saec.formtic.controller.auth;

import com.saec.formtic.controller.auth.authDTO.LoginDTO;
import com.saec.formtic.security.service.UserDetailsServiceImpl;
import com.saec.formtic.utils.CustomResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = {"*"})
public class AuthController {

    private final UserDetailsServiceImpl service;

    @Autowired
    public AuthController(UserDetailsServiceImpl service) {
        this.service = service;
    }

    @PostMapping(value = "/login", produces = "application/json")
    public ResponseEntity<CustomResponse<?>> login(@RequestBody @Valid LoginDTO request) {
        try{
            CustomResponse<?> response = service.login(request);
            return ResponseEntity.status(response.getStatus()).body(response);
        } catch(IllegalArgumentException | IllegalStateException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CustomResponse<>(
                    HttpStatus.BAD_REQUEST.value(),
                    ex.getMessage(),
                    true,
                    null
            ));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new CustomResponse<>(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Error during request",
                    true,
                    null
            ));
        }
    }
}
