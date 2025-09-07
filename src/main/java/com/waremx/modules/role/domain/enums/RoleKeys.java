package com.waremx.modules.role.domain.enums;

import lombok.Getter;

@Getter
public enum RoleKeys {
    IN_ROLE_NAME("in_role_name"),
    IN_CREATE_ROLE_DTO("in_create_role_dto")
    ;

    private final String key;
    RoleKeys(String key) {
        this.key = key;
    }
}
