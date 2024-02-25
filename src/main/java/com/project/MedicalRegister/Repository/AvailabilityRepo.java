package com.project.MedicalRegister.Repository;

import com.project.MedicalRegister.Model.Availability;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AvailabilityRepo extends JpaRepository<Availability, Long> {

    Optional<Iterable<Availability>> findAllByDoctorId(Long doctorId);
    Optional<Availability> findByDoctorIdAndDay(Long doctorId, String day);

}
