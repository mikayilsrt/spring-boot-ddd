package com.spiralin.spiralinapp.domain.valueObjects.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequest {
    @NotBlank(message = "Username is required")
    @NotNull(message = "Username is Null")
    private String username;

    @NotBlank(message = "Email is required")
    @NotNull(message = "Email is Null")
    @Email
    private String email;

    @NotBlank(message = "Password is required")
    @NotNull(message = "Password is Null")
    private String password;
}
