package com.waremx.common.core.entities;

import lombok.Getter;

@Getter
public enum MocaResponseCodes {
    //Roles
    CREATE_ROLE("MOCA-ROLE-001", 201, "Create role", "Role created successfully"),
    GET_ROLE_BY_ID("MOCA-ROLE-002", 200, "Get role", "Role retrieved successfully"),
    GET_ROLE_BY_NAME("MOCA-ROLE-003", 200, "Get role by name", "Role retrieved successfully"),
    DISABLE_ROLE_BY_NAME("MOCA-ROLE-004", 200, "Disable role", "Role has been successfully deactivated"),
    UPDATE_ROLE("MOCA-ROLE-005", 200, "Update role", "The role has been successfully updated"),
    ;

    private final String mocaStatusCode;
    private final Integer statusCode;
    private final String status;
    private final String message;

    MocaResponseCodes(String mocaCode, Integer statusCode, String status, String message) {
        this.mocaStatusCode = mocaCode;
        this.statusCode = statusCode;
        this.status = status;
        this.message = message;
    }
}
