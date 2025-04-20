package com.masteryhub.e_commerce.dto.authenticationDto;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthenticationResponse {
    private String token;
    private String username;
}
