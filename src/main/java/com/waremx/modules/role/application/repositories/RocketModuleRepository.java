package com.waremx.modules.role.application.repositories;

import com.waremx.common.core.errors.MocaErrCodes;
import com.waremx.modules.role.domain.objects.RocketModule;
import io.vavr.control.Either;

public interface RocketModuleRepository {
    Either<MocaErrCodes, RocketModule> findModuleByName(String module);
}
