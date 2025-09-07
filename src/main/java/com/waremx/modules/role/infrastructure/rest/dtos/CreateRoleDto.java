package com.waremx.modules.role.infrastructure.rest.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Data
public class CreateRoleDto {

    @NotBlank(message = "Role name is mandatory")
    @NotEmpty(message = "Role name can not be empty")
    @NotNull
    @JsonProperty
    @Schema(example = "SOMETHING_ROLE")
    private String name;

    @NotBlank(message = "Display name is mandatory")
    @NotEmpty(message = "Display name can not be empty")
    @NotNull
    @JsonProperty
    @Schema(example = "Something")
    private String displayName;

    @JsonProperty
    @Schema(example = "true")
    private Boolean isProtected;
}
