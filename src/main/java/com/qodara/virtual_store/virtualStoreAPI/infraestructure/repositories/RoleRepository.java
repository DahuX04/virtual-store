package com.qodara.virtual_store.virtualStoreAPI.infraestructure.repositories;

import com.qodara.virtual_store.virtualStoreAPI.domain.entities.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Integer> {
    Role getRoleById(int id);
}
