package com.project.MedicalRegister.Repository;

import com.project.MedicalRegister.Model.WaitingAppointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WaitingAppointmentRepo extends JpaRepository<WaitingAppointment, Long> {

    Optional<List<WaitingAppointment>> findAllByMedicalCenterId (Long medicalCenterId);
}
