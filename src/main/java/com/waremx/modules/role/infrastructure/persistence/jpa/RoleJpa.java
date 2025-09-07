package com.waremx.modules.role.infrastructure.persistence.jpa;

import com.waremx.modules.role.domain.objects.Role;
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
@Table(name = PgQueryFactory.ROLE_TABLE_NAME, schema = PgQueryFactory.ROLE_SCHEMA_NAME)
public class RoleJpa {

    @Id
    @Column(name = PgQueryFactory.ROLE_ID, unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short roleId;

    @Column(name = PgQueryFactory.ROLE_NAME, nullable = false)
    private String name;

    @Column(name = PgQueryFactory.ROLE_DISPLAY_NAME, nullable = false)
    private String displayName;

    @Column(name = PgQueryFactory.CREATED_BY, nullable = false)
    private String createdBy;

    @Column(name = PgQueryFactory.UPDATED_BY, nullable = false)
    private String updatedBy;

    @Column(name = PgQueryFactory.CREATED_AT, nullable = false)
    private LocalDateTime createdAt;

    @Column(name = PgQueryFactory.UPDATED_AT, nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = PgQueryFactory.IS_ACTIVE, nullable = false)
    private Boolean isActive;

    @Column(name = PgQueryFactory.IS_PROTECTED, nullable = false)
    private Boolean isProtected;

    public static RoleJpa fromEntity(Role role) {
        return RoleJpa.builder()
                .name(role.getName())
                .displayName(role.getDisplayName())
                .createdBy(role.getCreatedBy())
                .updatedBy(role.getUpdatedBy())
                .createdAt(role.getCreatedAt())
                .updatedAt(role.getUpdatedAt())
                .isActive(role.getIsActive())
                .isProtected(role.getIsProtected())
                .build();
    }

    public Role toEntity() {
        return Role.builder()
                .roleId(this.roleId)
                .name(this.name)
                .displayName(this.displayName)
                .createdBy(this.createdBy)
                .updatedBy(this.updatedBy)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .isActive(this.isActive)
                .isProtected(this.isProtected)
                .build();
    }
}

