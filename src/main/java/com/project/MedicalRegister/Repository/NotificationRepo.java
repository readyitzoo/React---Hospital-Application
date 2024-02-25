package com.project.MedicalRegister.Repository;

import com.project.MedicalRegister.Model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NotificationRepo extends JpaRepository<Notification, Long> {

    Optional<List<Notification>> findAllByPatientId(Long patientId);
    Optional<List<Notification>> findAllByDoctorId(Long doctorId);
}
