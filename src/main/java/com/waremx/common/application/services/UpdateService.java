package com.waremx.common.application.services;

import com.waremx.common.core.errors.MocaErrCodes;
import io.vavr.control.Either;

public interface UpdateService<T, I, O> {
    Either<MocaErrCodes, O> update(T id, I input);
}
