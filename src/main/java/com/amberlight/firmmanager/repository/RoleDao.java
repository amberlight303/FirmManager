package com.amberlight.firmmanager.repository;

import com.amberlight.firmmanager.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository interface for objects of {@link Role} class.
 *
 * @author Oleh Koryachenko
 * @version 1.0
 */
public interface RoleDao extends JpaRepository<Role, Integer> {
}
