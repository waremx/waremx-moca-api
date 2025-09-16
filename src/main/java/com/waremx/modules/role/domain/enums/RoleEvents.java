package com.waremx.modules.role.domain.enums;

import lombok.Getter;

@Getter
public enum RoleEvents {
    EVENT("event"),
    GET_ROLE_BY("get_role_by"),
    CREATE_ROLE("create_role"),
    UPDATE_ROLE("update_role"),
    DISABLE_ROLE("disable_role"),
    ENABLE_ROLE("enable_role"),
    ;

    private final String event;
    RoleEvents(String event) {
        this.event = event;
    }
}
