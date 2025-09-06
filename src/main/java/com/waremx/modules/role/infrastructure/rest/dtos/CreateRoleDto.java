package com.waremx.modules.role.infrastructure.rest.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CreateRoleDto {
    @NotBlank(message = "Role name is mandatory")
    @NotEmpty(message = "Role name can not be empty")
    @NotNull
    @JsonProperty
    private String roleName;
}
