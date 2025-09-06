package com.waremx.common.core.entities;

import java.time.LocalDateTime;
import java.util.List;

import com.waremx.common.core.errors.MocaErr;
import com.waremx.common.core.errors.MocaErrResponse;
import com.waremx.common.core.errors.MocaErrCodes;

import jakarta.ws.rs.core.Response;

public class MocaResponseMapper {
    public static <T> Response toResponse(MocaResponseCodes code, T data) {
        MocaResponse<T> response = MocaResponse.<T>builder()
                .mocaStatusCode(code.getMocaStatusCode())
                .statusCode(code.getStatusCode())
                .status(code.getStatus())
                .message(code .getMessage())
                .timestamp(LocalDateTime.now())
                .data(data)
                .build();

        return Response.status(response.getStatusCode())
            .entity(response)
            .build();
    }

    public static Response toErr(MocaErrCodes code, String path) {
        MocaErr err = MocaErr.builder()
                .path(path)
                .mocaErrorCode(code.getMocaErrorCode())
                .statusCode(code.getStatusCode())
                .errorCode(code.getErrorCode())
                .message(code.getMessage())
                .build();

        MocaErrResponse response = MocaErrResponse.builder()
                .path(path)
                .timestamp(LocalDateTime.now())
                .errors(List.of(err))
                .build();

        return Response.status(err.getStatusCode())
                .entity(response)
                .build();

    }
}
