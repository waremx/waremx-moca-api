package com.waremx.modules.role.infrastructure.persistence.jpa;

import com.waremx.modules.role.domain.objects.RoleRocketModuleHub;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = PgQueryFactory.HUB_TABLE_NAME, schema = PgQueryFactory.SCHEMA_NAME)
public class RoleRocketModuleHubJpa {

    @Id
    @Column(name = PgQueryFactory.HUB_ID, length = 21, nullable = false, unique = true)
    private String hubId;

    @Column(name = PgQueryFactory.HUB_ROLE_NAME, nullable = false, length = 45)
    private String hubRoleName;

    @Column(name = PgQueryFactory.HUB_CREATED_BY, nullable = false, length = 45)
    private String createdBy;

    @Column(name = PgQueryFactory.HUB_UPDATED_BY, nullable = false, length = 45)
    private String updatedBy;

    @Column(name = PgQueryFactory.HUB_CREATED_AT, nullable = false)
    private LocalDateTime createdAt;

    @Column(name = PgQueryFactory.HUB_TAB_UPDATED_AT, nullable = false)
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = PgQueryFactory.HUB_ROLE_ID, nullable = false)
    private RoleJpa role;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = PgQueryFactory.HUB_MODULE_ID, nullable = false)
    private RocketModuleJpa module;

    public static RoleRocketModuleHubJpa fromEntity(RoleRocketModuleHub entity, RoleJpa role, RocketModuleJpa module) {
        return RoleRocketModuleHubJpa.builder()
                .hubId(entity.getHubId())
                .hubRoleName(entity.getHubRoleName())
                .role(role)
                .module(module)
                .createdBy(entity.getCreatedBy())
                .updatedBy(entity.getUpdatedBy())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public RoleRocketModuleHub toEntity() {
        return RoleRocketModuleHub.builder()
                .hubId(this.hubId)
                .hubRoleName(this.hubRoleName)
                .roleId(role.getRoleId())
                .moduleId(this.module.getModuleId())
                .createdBy(this.createdBy)
                .updatedBy(this.updatedBy)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .build();
    }
}
