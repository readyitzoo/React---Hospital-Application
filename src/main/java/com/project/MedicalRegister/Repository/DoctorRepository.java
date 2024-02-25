package com.project.MedicalRegister.Repository;

import com.project.MedicalRegister.Model.Doctor;
import com.project.MedicalRegister.Model.MedicalCenter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    Optional<Doctor> findByDoctorId(Long doctorId);
    Optional<Iterable<Doctor>> findAllByMedicalCenterId(Long medicalCenterId);
    Optional<Iterable<Doctor>> findAllBySpecializationId(Long specializationId);
}
