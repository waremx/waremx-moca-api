package com.waremx.common.core.entities;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MocaResponse<T> {
    private String mocaStatusCode;
    private Integer statusCode;
    private String status;
    private String message;
    private LocalDateTime timestamp;
    private T data;
}
