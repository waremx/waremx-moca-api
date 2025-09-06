package com.waremx.common.application.services;

import com.waremx.common.core.errors.MocaErrCodes;

import io.vavr.control.Either;

public interface CreateService<I, O> {
    Either<MocaErrCodes, O> create(I inputDto);
}
