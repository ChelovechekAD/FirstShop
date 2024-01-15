package com.example.firstshop.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class LoginResponse {
    //@JsonProperty ("login")
    private String username;
    //@JsonProperty ("email")
    private String email;
    private String accessToken;
    private String refreshToken;
}
