package com.waremx.modules.role.infrastructure.persistence.dao;

import com.waremx.common.core.errors.MocaErrCodes;
import com.waremx.modules.role.application.repositories.RoleRocketModuleHubRepository;
import com.waremx.modules.role.domain.objects.RocketModule;
import com.waremx.modules.role.domain.objects.Role;
import com.waremx.modules.role.domain.objects.RoleRocketModuleHub;
import com.waremx.modules.role.infrastructure.persistence.jpa.PgQueryFactory;
import com.waremx.modules.role.infrastructure.persistence.jpa.RocketModuleJpa;
import com.waremx.modules.role.infrastructure.persistence.jpa.RoleJpa;
import com.waremx.modules.role.infrastructure.persistence.jpa.RoleRocketModuleHubJpa;
import com.waremx.modules.role.infrastructure.persistence.repositories.RoleRocketModuleHubJpaRepository;
import io.vavr.control.Either;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class RoleRocketModuleHubDao implements RoleRocketModuleHubRepository {

    @Inject
    RoleRocketModuleHubJpaRepository roleRocketModuleHubJpaRepository;

    @Inject
    EntityManager entityManager;

    @Transactional
    @Override
    public Either<MocaErrCodes, RoleRocketModuleHub> saveAssociation(RoleRocketModuleHub roleRocketModuleHub, Role role, RocketModule module) {

        Object[] row = (Object[]) this.entityManager.createNativeQuery(PgQueryFactory.GET_ROLE_BY_NAME)
                .setParameter(PgQueryFactory.PARAM_ROLE_NAME, role.getName())
                .getSingleResult();
        RoleJpa roleJpa = RoleJpa.builder()
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

        RoleRocketModuleHubJpa jpa = this.roleRocketModuleHubJpaRepository
                .saveAndFlush(
                        RoleRocketModuleHubJpa.fromEntity(roleRocketModuleHub, roleJpa, RocketModuleJpa.fromEntity(module))
                );
        return Either.right(jpa.toEntity());
    }

    @SuppressWarnings("unchecked")
    @Override
    public Either<MocaErrCodes, List<RoleRocketModuleHub>> listAssociation(String roleName, String moduleId) {
        List<Object[]> rows = (List<Object[]>) this.entityManager.createNativeQuery(PgQueryFactory.GET_ROLE_MODULE_ASSOCIATIONS)
                .setParameter(PgQueryFactory.PARAM_ROLE_NAME_VAL, roleName)
                .setParameter(PgQueryFactory.PARAM_MODULE_ID, moduleId)
                .getResultList();

        if (rows.isEmpty()) {
            return Either.right(new ArrayList<>());
        }

        List<RoleRocketModuleHub> list = rows.stream().map(row -> RoleRocketModuleHub.builder()
                .hubId(row[0].toString())
                .roleId(Short.parseShort(row[1].toString()))
                .moduleId(row[2].toString())
                .hubRoleName(row[3].toString())
                .createdBy(row[4].toString())
                .updatedBy(row[5].toString())
                .createdAt((LocalDateTime) row[6])
                .updatedAt((LocalDateTime) row[7])
                .build()
        ).toList();

        return Either.right(list);
    }
}
