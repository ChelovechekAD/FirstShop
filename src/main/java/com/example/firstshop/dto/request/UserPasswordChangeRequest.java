package com.example.firstshop.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class UserPasswordChangeRequest {
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9-.]+@([a-zA-Z-]+\\.)+[a-zA-Z-]{2,4}$", message = "email is not valid")
    private String email;

    @NotBlank
    @Size(min = 8, max = 20)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).*$",
            message = "password must contain at least one uppercase and lowercase letter")
    @Pattern(regexp = "^(?=.*\\d)(?=.*[~`!@#$%^&()_=+{}\\[\\]/|:;,\"<>?]).*$",
            message = "password must contain at least one number and special symbol")
    @Pattern(regexp = "^[^А-Яа-яЇїІіЄєҐґЁё]+$",
            message = "password must contain only latin letters")
    private String password;
}
