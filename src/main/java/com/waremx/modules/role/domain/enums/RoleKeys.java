package com.waremx.modules.role.domain.enums;

import lombok.Getter;

@Getter
public enum RoleKeys {
    INPUT_ROLE_NAME("input_role_name"),
    INPUT_CREATE_ROLE_DTO("input_create_role_dto")
    ;

    private final String key;
    RoleKeys(String key) {
        this.key = key;
    }
}
