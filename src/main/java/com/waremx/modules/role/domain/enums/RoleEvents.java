package com.waremx.modules.role.domain.enums;

import lombok.Getter;

@Getter
public enum RoleEvents {
    ACTION("action"),
    EVENT_GET_ROLE_BY("get_role_by"),
    EVENT_CREATE_ROLE("create_role")
    ;

    private final String event;
    RoleEvents(String event) {
        this.event = event;
    }
}
