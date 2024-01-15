package com.example.firstshop.dto.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExceptionResponse {
    private Integer HttpStatusCode;
    private String ExceptionMessage;
    private List<String> ExceptionDetails;
    private LocalDateTime TimeStamp;

}
