package com.waremx.modules.role.infrastructure.rest.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Data
public class UpdateRoleDto {
    @JsonProperty
    @Schema(example = "SOMETHING_ROLE")
    private String name;

    @JsonProperty
    @Schema(example = "Something")
    private String displayName;

    @JsonProperty
    @Schema(example = "true")
    private Boolean isProtected;
}
