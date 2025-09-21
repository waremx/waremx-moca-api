package com.waremx.modules.role.domain.contexts;

import com.waremx.common.core.errors.MocaErrCodes;
import com.waremx.common.mox.listeners.Observe;

import io.vavr.control.Either;
import lombok.Getter;

@Getter
public class RoleContext implements Observe<Either<MocaErrCodes, ?>> {

    private Either<MocaErrCodes, ?> result;

    @Override
    public void update(Either<MocaErrCodes, ?> event) {
        this.result = event;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> Either<MocaErrCodes, T> get() {
        return (Either<MocaErrCodes, T>) result;
    }
}
