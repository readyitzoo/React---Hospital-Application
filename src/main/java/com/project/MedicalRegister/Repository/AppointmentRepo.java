package com.project.MedicalRegister.Repository;

import com.project.MedicalRegister.Model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppointmentRepo extends JpaRepository<Appointment, Long> {

    Optional<Iterable<Appointment>> findAllByDoctorId(Long doctorId);
    Optional<Iterable<Appointment>> findAllByPatientId(Long patientId);

}
