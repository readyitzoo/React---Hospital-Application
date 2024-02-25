package com.project.MedicalRegister.Repository;

import com.project.MedicalRegister.Model.Specialization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpecializationRepo extends JpaRepository<Specialization, Long> {

    Optional<Specialization> findBySpecializationName(String name);
}
