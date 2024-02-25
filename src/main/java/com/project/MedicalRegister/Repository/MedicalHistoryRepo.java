package com.project.MedicalRegister.Repository;

import com.project.MedicalRegister.Model.MedicalHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MedicalHistoryRepo extends JpaRepository<MedicalHistory, Long> {

    Optional<Iterable<MedicalHistory>> findAllByPatientId(Long patientId);
    Optional<MedicalHistory> findByMedicalHistoryId (Long medicalHistoryId);

}
