package com.waremx.modules.role.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    private Short roleId;
    private String roleName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean isActive;
}