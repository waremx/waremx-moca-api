package com.waremx.common.core.errors;

import lombok.Getter;

@Getter
public enum MocaErrCodes {
    BAD_REQUEST("MOCA-SYS-ERR001", 400, "Bad Request", "Invalid request format. Please check the request body"),
    UNIQUENESS_RULE("MOCA-SYS-ERR002", 400, "Bad Request", "Insertion error. This element already exists"),
    NOT_NULL_VALUE("MOCA-SYS-ERR003", 400, "Bad Request", "Value cannot be null. Please provide a valid value for the required field"),
    NOT_BLANK_VALUE("MOCA-SYS-ERR004", 400, "Bad Request", "Value cannot be black. Please provide a valid value for the required field"),
    UNAUTHORIZED("MOCA-SYS-ERR005", 401, "Unauthorized", "Not valid credentials, check email or password"),
    NOT_FOUND("MOCA-SYS-ERR006", 404, "Not Found", "Resource not found"),
    RESOURCE_NOT_AVAILABLE("MOCA-SYS-ERR007", 404, "Not Found", "Resource not available"),
    INTERNAL_SERVER_ERROR("MOCA-SYS-ERR007", 500, "Internal Server Error", "Oops... Something went wrong. An unexpected error occurred. Please try again later."),
    ERROR_DB("MOCA-SYS-ERR008", 500, "Internal Server Error", "Oops... Something went wrong. Unable to process the request due to a database failure."),
    ERROR_NULL("MOCA-SYS-ERR009", 500, "Internal Server Error", "Oops... Something went wrong. Unexpected null value encountered while processing request."),

    //Role Error Codes
    ROLE_NOT_FOUND("MOCA-ROL-ERR001", 404, "Not Found", "Role not found"),
    ROLE_EMPTY_LIST("MOCA-ROL-ERR005", 404, "Not Found", "Request completed successfully, but no records were found."),
    ROLE_NOT_AVAILABLE("MOCA-ROL-ERR002", 400, "Bad Request", "Role not available"),
    ROLE_ERROR_TO_CREATE("MOCA-ROL-ERR003", 500, "Internal Server Error", "Oops... Something went wrong, role could not be created"),
    ROLE_ERROR_TO_UPDATE("MOCA-ROL-ERR006", 500, "Internal Server Error", "Oops... Something went wrong, role could not be updated"),
    ROLE_ALREADY_EXISTS("MOCA-ROL-ERR004", 400, "Bad Request", "Oops... Something went wrong, this role already exists"),

    ;


    private final String mocaErrorCode;
    private final Integer statusCode;
    private final String errorCode;
    private final String message;

    MocaErrCodes(String mocaErrorCode, Integer statusCode, String errorCode, String message) {
        this.mocaErrorCode = mocaErrorCode;
        this.statusCode = statusCode;
        this.errorCode = errorCode;
        this.message = message;
    }
}
