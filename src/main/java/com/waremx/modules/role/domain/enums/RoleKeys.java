package com.waremx.modules.role.domain.enums;

import lombok.Getter;

@Getter
public enum RoleKeys {
    RESULT("result"),
    INPUT_ROLE_NAME("input_role_name"),
    INPUT_CREATE_ROLE_DTO("input_create_role_dto"),
    INPUT_UPDATE_ROLE_DTO("input_update_role_dto"),
    ;

    private final String key;
    RoleKeys(String key) {
        this.key = key;
    }
}
