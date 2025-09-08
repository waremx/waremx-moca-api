package com.waremx.modules.role.domain.handlers;

import com.waremx.common.core.errors.MocaErrCodes;
import com.waremx.common.core.patterns.Handler;
import com.waremx.common.mox.uni.Context;
import com.waremx.modules.role.domain.objects.Role;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

import static com.waremx.modules.role.domain.enums.RoleKeys.INPUT_ROLE_NAME;

@AllArgsConstructor
public class FilterInputHandler extends Handler<Role, MocaErrCodes> {

    private static final Logger LOGGER = LoggerFactory.getLogger(FilterInputHandler.class);

    private String event;

    @Override
    public Handler<Role, MocaErrCodes> execute(Context<Role, MocaErrCodes> context) {
        String[] words = context.<String>get(INPUT_ROLE_NAME.getKey()).orElseThrow().split("_");

        if (!words[words.length - 1].equals("ROLE")) {
            LOGGER.error("[ERROR]: The role name must end with \"_ROLE\"");
            context.err(MocaErrCodes.ROLE_FORMAT_ERROR);
            context.emit(this.event, Optional.empty());
            return checkNext(null);
        }

        return checkNext(context);
    }
}
