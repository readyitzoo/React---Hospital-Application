package com.project.MedicalRegister.Repository;

import com.project.MedicalRegister.Model.WaitingApprovals;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WaitingApprovalsRepo extends JpaRepository<WaitingApprovals, Long> {

    Optional<Iterable<WaitingApprovals>> findAllByMedicalCenterID (Long medicalCenterID);
}
