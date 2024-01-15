package com.example.firstshop.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Builder
@RequiredArgsConstructor
@Getter
@Setter
public class DeleteUserRequest {
    private final String email;
    private final String password;
    private final boolean adminRequest;
}
