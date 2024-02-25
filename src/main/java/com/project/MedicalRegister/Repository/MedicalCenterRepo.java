package com.project.MedicalRegister.Repository;

import com.project.MedicalRegister.Model.MedicalCenter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MedicalCenterRepo extends JpaRepository<MedicalCenter, Long> {

    Optional<MedicalCenter> findByName(String name);
}
