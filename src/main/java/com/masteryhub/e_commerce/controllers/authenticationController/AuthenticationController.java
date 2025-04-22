package com.masteryhub.e_commerce.controllers.authenticationController;

import com.masteryhub.e_commerce.dto.authenticationDto.AuthenticationResponse;
import com.masteryhub.e_commerce.dto.userDto.LoginDto;
import com.masteryhub.e_commerce.dto.userDto.RegisterDto;
import com.masteryhub.e_commerce.service.authenticationService.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterDto registerDto) {
        return authenticationService.register(registerDto);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@Valid @RequestBody LoginDto loginDto) {
        return authenticationService.login(loginDto);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        return authenticationService.logout();
    }
}
