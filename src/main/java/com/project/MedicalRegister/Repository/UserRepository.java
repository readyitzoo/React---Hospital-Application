package com.project.MedicalRegister.Repository;

import com.project.MedicalRegister.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByCnp(String CNP);
	Optional<User> findByEmail(String email);
	Optional<Iterable<User>> findAllByRoleId(Integer roleId);
}