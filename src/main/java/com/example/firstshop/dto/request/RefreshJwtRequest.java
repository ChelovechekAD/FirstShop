package com.example.firstshop.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RefreshJwtRequest {
    public String refreshToken;
}
