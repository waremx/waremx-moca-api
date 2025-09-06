package com.waremx.common.core.errors;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MocaErrResponse {
    private String path;
    private LocalDateTime timestamp;
    private List<MocaErr> errors;
}
