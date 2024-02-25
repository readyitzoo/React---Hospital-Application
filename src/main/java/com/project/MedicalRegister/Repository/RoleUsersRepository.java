package com.project.MedicalRegister.Repository;

import com.project.MedicalRegister.Model.RoleUsers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleUsersRepository extends JpaRepository<RoleUsers, Integer> {
    Optional<RoleUsers> findByRoleName(String name);
    Optional<RoleUsers> findByRoleId(Integer id);
}
