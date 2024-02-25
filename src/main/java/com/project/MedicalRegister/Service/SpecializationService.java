package com.project.MedicalRegister.Service;

import com.project.MedicalRegister.Exceptions.AppException;
import com.project.MedicalRegister.Model.Specialization;
import com.project.MedicalRegister.Repository.SpecializationRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SpecializationService {

    private final SpecializationRepo specializationRepository;

    @Transactional
    public Specialization addSpecialization(String specializationName) {
        Specialization specialization = new Specialization();
        specialization.setSpecializationName(specializationName);
        Specialization saved = specializationRepository.save(specialization);

        return saved;
    }

    public Iterable<Specialization> getAllSpecializations() {
        Iterable<Specialization> specializations = specializationRepository.findAll();
        if (specializations.iterator().hasNext()) {
            return specializations;
        } else {
            throw new AppException("No specializations found", HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public void updateSpecialization(Long id, String specializationName) {
        Optional<Specialization> optionalSpecialization = specializationRepository.findById(id);
        if (optionalSpecialization.isPresent()) {
            Specialization specialization = optionalSpecialization.get();
            specialization.setSpecializationName(specializationName);
            specializationRepository.save(specialization);
        } else {
            throw new AppException("Specialization not found", HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public void deleteSpecialization(Long id) {
        Optional<Specialization> optionalSpecialization = specializationRepository.findById(id);
        if (optionalSpecialization.isPresent()) {
            specializationRepository.deleteById(id);
        } else {
            throw new AppException("Specialization not found", HttpStatus.BAD_REQUEST);
        }
    }
}
