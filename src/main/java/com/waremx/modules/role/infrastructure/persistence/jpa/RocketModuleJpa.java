package com.waremx.modules.role.infrastructure.persistence.jpa;

import java.time.LocalDateTime;
import java.util.Set;

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
    @Column(name = PgQueryFactory.MODULE_ID, unique = true, nullable = false, length = 45)
    private String moduleId;

    @Column(name = PgQueryFactory.MODULE_DISPLAY_NAME, nullable = false, length = 45)
    private String displayName;

    @Column(name = PgQueryFactory.MODULE_DESCRIPTION, nullable = false)
    private String description;

    @Column(name = PgQueryFactory.MODULE_CREATED_BY, insertable = false, updatable = false, length = 45)
    private String createdBy;

    @Column(name = PgQueryFactory.MODULE_UPDATED_BY, insertable = false, updatable = false, length = 45)
    private String updatedBy;

    @Column(name = PgQueryFactory.MODULE_CREATED_AT, insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = PgQueryFactory.MODULE_UPDATED_AT, insertable = false, updatable = false)
    private LocalDateTime updatedAt;

    @Column(name = PgQueryFactory.MODULE_IS_ACTIVE, nullable = false)
    private Boolean isActive;

    @OneToMany(mappedBy = "module", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RoleRocketModuleHubJpa> modules;

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
