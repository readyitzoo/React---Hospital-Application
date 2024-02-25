package com.project.MedicalRegister.Repository;

import com.project.MedicalRegister.Model.ContractMedicalCenter;
import com.project.MedicalRegister.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ContractRepository extends JpaRepository<ContractMedicalCenter, Long> {

    Optional<ContractMedicalCenter> findByContractId(Long contractId);
    Optional<ContractMedicalCenter> findByUserId(Long userId);
    List<ContractMedicalCenter> findAllByMedicalCenterId(Long medicalCenterId);
}
