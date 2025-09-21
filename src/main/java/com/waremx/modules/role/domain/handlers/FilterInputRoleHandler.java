package com.waremx.modules.role.domain.handlers;

import com.waremx.common.core.errors.MocaErrCodes;
import com.waremx.common.core.patterns.Handler;
import com.waremx.common.mox.uni.Context;
import com.waremx.modules.role.domain.objects.Role;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.waremx.modules.role.domain.enums.RoleKeys.INPUT_ROLE_NAME;

@AllArgsConstructor
public class FilterInputRoleHandler extends Handler<Role, MocaErrCodes> {

    private static final Logger LOGGER = LoggerFactory.getLogger(FilterInputRoleHandler.class);

    @Override
    public Handler<Role, MocaErrCodes> execute(Context<Role, MocaErrCodes> context) {

        LOGGER.info("[HANDLER]: FilterInputRoleHandler");

        String[] words = context.<String>get(INPUT_ROLE_NAME.getKey()).orElseThrow().split("_");

        if ((words.length == 2 && words[0].isBlank()) || (words[0].length() < 3)) {
            LOGGER.error("[ERROR]: This role does not have a valid name format");
            context.err(MocaErrCodes.ROLE_FORMAT_ERROR);
            return checkNext(null);
        }

        if (!words[words.length - 1].equals("ROLE")) {
            LOGGER.error("[ERROR]: The role name must end with \"_ROLE\"");
            context.err(MocaErrCodes.ROLE_FORMAT_ERROR);
            return checkNext(null);
        }

        return checkNext(context);
    }
}
