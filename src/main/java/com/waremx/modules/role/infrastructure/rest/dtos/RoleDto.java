package com.waremx.modules.role.infrastructure.rest.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.waremx.modules.role.domain.objects.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleDto {

    @JsonProperty
    private String name;

    @JsonProperty
    private String displayName;

    @JsonProperty
    private LocalDateTime createdAt;

    @JsonProperty
    private LocalDateTime updatedAt;

    @JsonProperty
    private Boolean isActive;

    @JsonProperty
    private Boolean isProtected;

    public static RoleDto from(Role role) {
        return RoleDto.builder()
                .name(role.getName())
                .displayName(role.getDisplayName())
                .createdAt(role.getCreatedAt())
                .updatedAt(role.getUpdatedAt())
                .isActive(role.getIsActive())
                .isProtected(role.getIsProtected())
                .build();
    }
}
