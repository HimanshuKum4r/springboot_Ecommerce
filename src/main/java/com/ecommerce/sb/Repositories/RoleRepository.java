package com.ecommerce.sb.Repositories;

import com.ecommerce.sb.model.AppRole;
import com.ecommerce.sb.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByRoleName(AppRole appRole);
}
