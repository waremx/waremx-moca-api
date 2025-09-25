package com.waremx.modules.role.infrastructure.persistence.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = PgQueryFactory.HUB_TABLE_NAME, schema = PgQueryFactory.SCHEMA_NAME)
public class RoleModuleHubJpa {
    @Id
    @Column(name = PgQueryFactory.HUB_ID, unique = true, nullable = false, length = 21)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = PgQueryFactory.MODULE_ID, nullable = false)
    private RocketModuleJpa module;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = PgQueryFactory.ROLE_ID, nullable = false)
    private RoleJpa role;

    @Column(name = PgQueryFactory.ROLE_NAME, nullable = false, length = 45)
    private String name;
}
