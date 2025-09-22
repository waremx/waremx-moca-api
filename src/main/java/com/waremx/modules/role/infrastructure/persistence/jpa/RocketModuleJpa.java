package com.waremx.modules.role.infrastructure.persistence.jpa;

import com.waremx.modules.role.domain.objects.RocketModule;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = PgQueryFactory.MODULE_TABLE_NAME, schema = PgQueryFactory.SCHEMA_NAME)
public class RocketModuleJpa {

    @Id
    @Column(name = PgQueryFactory.MODULE_ID, unique = true, nullable = false)
    private String moduleId;

    @Column(name = PgQueryFactory.MODULE_DISPLAY_NAME, nullable = false)
    private String displayName;

    @Column(name = PgQueryFactory.MODULE_DESCRIPTION, nullable = false)
    private String description;

    @Column(name = PgQueryFactory.MODULE_IS_ACTIVE, nullable = false)
    private Boolean isActive;

    public static RocketModuleJpa fromEntity(RocketModule module) {
        return RocketModuleJpa.builder()
                .moduleId(module.getModuleId())
                .displayName(module.getDisplayName())
                .description(module.getDescription())
                .isActive(module.getIsActive())
                .build();
    }
    
    public RocketModule toEntity() {
        return RocketModule.builder()
                .moduleId(this.moduleId)
                .displayName(this.displayName)
                .description(this.description)
                .isActive(this.isActive)
                .build();
    }
}
