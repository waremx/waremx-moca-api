package com.waremx.modules.role.infrastructure.persistence.repositories;

import com.waremx.modules.role.infrastructure.persistence.jpa.RoleRocketModuleHubJpa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRocketModuleHubJpaRepository extends JpaRepository<RoleRocketModuleHubJpa, String> {
}
