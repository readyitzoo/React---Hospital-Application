package com.project.MedicalRegister.Service;

import com.project.MedicalRegister.DTO.InvestigationDto;
import com.project.MedicalRegister.Exceptions.AppException;
import com.project.MedicalRegister.Model.Investigation;
import com.project.MedicalRegister.Repository.InvestigationRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class InvestigationService {

    private final InvestigationRepo investigationRepository;

    @Transactional
    public Investigation addInvestigationToDoctor(InvestigationDto investigationDto) {
        Investigation investigation = new Investigation();
        investigation.setDoctorId(investigationDto.getDoctorId());
        investigation.setInvestigationName(investigationDto.getInvestigationName());
        investigation.setPrice(investigationDto.getPrice());
        Investigation saved = investigationRepository.save(investigation);

        return saved;
    }

    public Iterable<Investigation> getInvestigationsByDoctorId(Long doctorId) {
        Iterable<Investigation> investigations = investigationRepository.findByDoctorId(doctorId)
                .orElseThrow(() -> new AppException("No investigations found for doctor with ID: " + doctorId, HttpStatus.BAD_REQUEST));

        return investigations;
    }

    @Transactional
    public void updateInvestigation(Long id, InvestigationDto investigationDto) {
        Optional<Investigation> optionalInvestigation = investigationRepository.findById(id);
        if (optionalInvestigation.isPresent()) {
            Investigation investigation = optionalInvestigation.get();
            investigation.setDoctorId(investigationDto.getDoctorId());
            investigation.setInvestigationName(investigationDto.getInvestigationName());
            investigation.setPrice(investigationDto.getPrice());
            investigationRepository.save(investigation);
        } else {
            throw new AppException("Investigation not found", HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public void deleteInvestigation(Long id) {
        Optional<Investigation> optionalInvestigation = investigationRepository.findById(id);
        if (optionalInvestigation.isPresent()) {
            investigationRepository.deleteById(id);
        } else {
            throw new AppException("Investigation not found", HttpStatus.BAD_REQUEST);
        }
    }



}
