package com.ecommerce.sb.security.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;
@Data
public class SignupRequest {
    @NotBlank
    @Size(min = 3,max = 20)
    private String username;
    @NotBlank
    @Size(max=30)
    @Email
    private String email;

    private Set<String> role;
    @NotBlank
    private String passwrd;

}
