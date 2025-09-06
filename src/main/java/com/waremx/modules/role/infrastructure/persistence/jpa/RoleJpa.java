package com.waremx.modules.role.infrastructure.persistence.jpa;

import com.waremx.modules.role.domain.entities.Role;
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
    private String roleName;

    @Column(name = PgQueryFactory.CREATED_AT, nullable = false)
    private LocalDateTime createdAt;

    @Column(name = PgQueryFactory.UPDATED_AT, nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = PgQueryFactory.IS_ACTIVE, nullable = false)
    private Boolean isActive;

    public static RoleJpa fromEntity(Role role) {
        return RoleJpa.builder()
                .roleName(role.getRoleName())
                .createdAt(role.getCreatedAt())
                .updatedAt(role.getUpdatedAt())
                .isActive(role.getIsActive())
                .build();
    }

    public Role toEntity() {
        return Role.builder()
                .roleId(this.roleId)
                .roleName(this.roleName)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .isActive(this.isActive)
                .build();
    }
}

