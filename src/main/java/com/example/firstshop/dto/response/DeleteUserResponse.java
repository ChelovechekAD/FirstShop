package com.example.firstshop.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Builder
@RequiredArgsConstructor
@Getter
@Setter
public class DeleteUserResponse {
    private final String status;
    private final String message;
}
