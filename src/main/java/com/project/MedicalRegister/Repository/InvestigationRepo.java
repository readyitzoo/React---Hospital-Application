package com.project.MedicalRegister.Repository;

import com.project.MedicalRegister.Model.Investigation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InvestigationRepo extends JpaRepository<Investigation, Long> {

    Optional<Iterable<Investigation>> findByDoctorId (Long doctorId);
}
