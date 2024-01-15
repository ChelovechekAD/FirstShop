package com.example.firstshop.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class UserRegistrationRequest {
    private String username;
    private String password;

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9-.]+@([a-zA-Z-]+\\.)+[a-zA-Z-]{2,4}$", message = "email is not valid")
    private String email;
    private String passwordConfirm;
}
