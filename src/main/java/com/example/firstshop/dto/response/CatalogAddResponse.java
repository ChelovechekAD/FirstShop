package com.example.firstshop.dto.response;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
public class CatalogAddResponse {
    private final String httpCode;
    private final String message;

}
