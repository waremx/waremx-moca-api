package com.waremx.modules.role.domain.contexts;

import com.waremx.common.mox.listeners.Observe;
import com.waremx.modules.role.domain.entities.Role;

import java.util.Optional;


public class RoleContext implements Observe<Optional<Role>> {

    private Optional<Role> result;

    @Override
    public void update(Optional<Role> event) {
        this.result = event;
    }

    @Override
    public Optional<Role> get() {
        return this.result;
    }
}
