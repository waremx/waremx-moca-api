package com.waremx.modules.role.application.adapters;

import com.waremx.common.application.services.GetByService;
import com.waremx.common.core.errors.MocaErrCodes;
import com.waremx.modules.role.infrastructure.rest.dtos.RocketModuleDto;

import io.vavr.control.Either;

public class GetDetailRocketModule implements GetByService<String, RocketModuleDto> {

    @Override
    public Either<MocaErrCodes, RocketModuleDto> getService(String input) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getByService'");
    }
    
}
