package com.waremx.modules.role.domain.contexts;

import com.waremx.common.core.errors.MocaErrCodes;
import com.waremx.common.mox.listeners.Observe;
import com.waremx.modules.role.domain.objects.Role;

import io.vavr.control.Either;
import lombok.Getter;

@Getter
public class RoleContext implements Observe<Either<MocaErrCodes, Role>> {

    private Either<MocaErrCodes, Role> result;

    @Override
    public void update(Either<MocaErrCodes, Role> event) {
        this.result = event;
    }

    @Override
    public Either<MocaErrCodes, Role> get() {
        return result;
    }
}
