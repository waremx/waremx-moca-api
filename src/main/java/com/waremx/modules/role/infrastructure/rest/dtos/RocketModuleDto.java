package com.waremx.modules.role.infrastructure.rest.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.waremx.modules.role.domain.objects.RocketModule;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RocketModuleDto {

    @JsonProperty
    private String moduleId;

    @JsonProperty
    private String displayName;

    @JsonProperty
    private String description;

    @JsonProperty
    private Boolean isActive;

    public static RocketModuleDto from(RocketModule module) {
        return RocketModuleDto.builder()
                .moduleId(module.getModuleId())
                .displayName(module.getDisplayName())
                .description(module.getDescription())
                .isActive(module.getIsActive())
                .build();
    }
    
}
