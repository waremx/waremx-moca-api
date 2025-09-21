package com.waremx.modules.role.infrastructure.persistence;

import com.waremx.common.core.errors.MocaErrCodes;
import com.waremx.modules.role.application.repositories.RoleRepository;
import com.waremx.modules.role.domain.objects.Role;
import com.waremx.modules.role.infrastructure.persistence.jpa.PgQueryFactory;
import com.waremx.modules.role.infrastructure.persistence.jpa.RoleJpa;
import com.waremx.modules.role.infrastructure.persistence.jpa.RoleJpaRepository;

import io.vavr.control.Either;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import org.hibernate.exception.ConstraintViolationException;

@ApplicationScoped
public class RoleDao implements RoleRepository {

    @Inject
    RoleJpaRepository roleJpaRepository;

    @Inject
    EntityManager entityManager;

    @Transactional
    @Override
    public Optional<Role> create(Role role) {
        try {
            return Optional.ofNullable(
                    this.roleJpaRepository.saveAndFlush(RoleJpa.fromEntity(role)).toEntity()
            );
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Role> findRoleByName(String name) {
        try {
            Object[] row = (Object[]) this.entityManager.createNativeQuery(PgQueryFactory.GET_ROLE_BY_NAME)
                    .setParameter(PgQueryFactory.PARAM_ROLE_NAME, name)
                    .getSingleResult();

            return Optional.ofNullable(toRole(row));
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Transactional
    @Override
    public Optional<Role> disable(Role role) {
        try {
            int count = entityManager.createNativeQuery(PgQueryFactory.DISABLE_ROLE)
                    .setParameter(PgQueryFactory.PARAM_ROLE_NAME, role.getName())
                    .setParameter(PgQueryFactory.PARAM_ROLE_UPDATED_BY, role.getUpdatedBy())
                    .setParameter(PgQueryFactory.PARAM_ROLE_UPDATED_AT, role.getUpdatedAt())
                    .executeUpdate();

            if (count <= 0) {
                return Optional.empty();
            }

            Object[] row = (Object[]) this.entityManager.createNativeQuery(PgQueryFactory.GET_ROLE_BY_NAME)
                    .setParameter(PgQueryFactory.PARAM_ROLE_NAME, role.getName())
                    .getSingleResult();

            return Optional.ofNullable(toRole(row));
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Transactional
    @Override
    public Either<MocaErrCodes, Role> update(String name, Role role) {
        try {
            int count = entityManager.createNativeQuery(PgQueryFactory.UPDATE_ROLE)
                    .setParameter(PgQueryFactory.PARAM_ROLE_NAME_VAL, role.getName())
                    .setParameter(PgQueryFactory.PARAM_ROLE_DISPLAY_NAME, role.getDisplayName())
                    .setParameter(PgQueryFactory.PARAM_ROLE_IS_PROTECTED, role.getIsProtected())
                    .setParameter(PgQueryFactory.PARAM_ROLE_NAME, name)
                    .setParameter(PgQueryFactory.PARAM_ROLE_UPDATED_BY, role.getUpdatedBy())
                    .setParameter(PgQueryFactory.PARAM_ROLE_UPDATED_AT, role.getUpdatedAt())
                    .executeUpdate();

            if (count <= 0) {
                return Either.left(MocaErrCodes.ROLE_ERROR_TO_UPDATE);
            }

            Object[] row = (Object[]) this.entityManager.createNativeQuery(PgQueryFactory.GET_ROLE_BY_NAME)
                    .setParameter(PgQueryFactory.PARAM_ROLE_NAME, role.getName())
                    .getSingleResult();

            return Either.right(toRole(row));
        } catch (ConstraintViolationException e) {
            return Either.left(MocaErrCodes.ROLE_VALUE_ALREADY_EXISTS);
        }
    }

    private Role toRole(Object[] row) {
        Role role = Role.builder()
                .roleId((Short) row[0])
                .name((String) row[1])
                .displayName((String) row[2])
                .createdBy((String) row[3])
                .updatedBy((String) row[4])
                .createdAt((LocalDateTime) row[5])
                .updatedAt((LocalDateTime) row[6])
                .isActive((Boolean) row[7])
                .isProtected((Boolean) row[8])
                .build();

        return role;
    }
}
