package com.waremx.common.core.errors;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MocaErr {
    private String path;
    private String mocaErrorCode;
    private Integer statusCode;
    private String errorCode;
    private String message;
}
