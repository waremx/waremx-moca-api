package com.waremx.modules.role.infrastructure.persistence.repositories;

import com.waremx.modules.role.infrastructure.persistence.jpa.RoleJpa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleJpaRepository extends JpaRepository<RoleJpa, Long> {
}
