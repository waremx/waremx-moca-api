package com.waremx.common.application.services;

import java.util.List;

import com.waremx.common.core.errors.MocaErrCodes;

import io.vavr.control.Either;

public interface ListService<T> {
    Either<MocaErrCodes, List<T>> list(int limit, int offset);
}
