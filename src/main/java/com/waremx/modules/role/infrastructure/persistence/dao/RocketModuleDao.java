package com.waremx.modules.role.infrastructure.persistence.dao;

import com.waremx.common.core.errors.MocaErrCodes;
import com.waremx.modules.role.application.repositories.RocketModuleRepository;
import com.waremx.modules.role.domain.objects.RocketModule;
import com.waremx.modules.role.infrastructure.persistence.jpa.PgQueryFactory;
import io.vavr.control.Either;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

@ApplicationScoped
public class RocketModuleDao implements RocketModuleRepository {

    @Inject
    EntityManager entityManager;

    @Override
    public Either<MocaErrCodes, RocketModule> findModuleByName(String module) {
        try {
            Object[] row = (Object[]) this.entityManager.createNativeQuery(PgQueryFactory.GET_MODULE_BY_NAME)
                    .setParameter(PgQueryFactory.PARAM_MODULE_ID, module)
                    .getSingleResult();

            RocketModule found = RocketModule.builder()
                    .moduleId(row[0].toString())
                    .displayName(row[1].toString())
                    .description(row[2].toString())
                    .isActive(Boolean.parseBoolean(row[3].toString()))
                    .build();

            return Either.right(found);
        } catch (NoResultException e) {
            return Either.left(MocaErrCodes.ROLE_NOT_FOUND);
        } catch (Exception e) {
            return Either.left(MocaErrCodes.INTERNAL_SERVER_ERROR);
        }
    }
}
