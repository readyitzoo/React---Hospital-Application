package com.project.MedicalRegister.Service;

import com.project.MedicalRegister.DTO.MedicalCenterDto;
import com.project.MedicalRegister.DTO.UserDto;
import com.project.MedicalRegister.Exceptions.AppException;
import com.project.MedicalRegister.Model.MedicalCenter;
import com.project.MedicalRegister.Repository.MedicalCenterRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MedicalCenterService {

    private final MedicalCenterRepo medicalCenterRepository;

    @Transactional
    public MedicalCenter addMedicalCenter(MedicalCenterDto medicalCenterDto) {

        MedicalCenter medicalCenter = new MedicalCenter();
        medicalCenter.setLocation(medicalCenterDto.getLocation());
        medicalCenter.setName(medicalCenterDto.getName());
        MedicalCenter saved = medicalCenterRepository.save(medicalCenter);

        return saved;
    }

    public Iterable<MedicalCenter> getAllMedicalCenters() {
        Iterable<MedicalCenter> medicalCenters = medicalCenterRepository.findAll();
        if (medicalCenters.iterator().hasNext()) {
            return medicalCenters;
        } else {
            throw new AppException("No medical centers found", HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public void updateMedicalCenter(Long id, MedicalCenterDto medicalCenterDto) {
        Optional<MedicalCenter> optionalMedicalCenter = medicalCenterRepository.findById(id);
        if (optionalMedicalCenter.isPresent()) {
            MedicalCenter medicalCenter = optionalMedicalCenter.get();
            medicalCenter.setName(medicalCenterDto.getName());
            medicalCenter.setLocation(medicalCenterDto.getLocation());
            medicalCenterRepository.save(medicalCenter);
        } else {
            throw new AppException("Medical center not found", HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public void deleteMedicalCenter(Long id) {
        Optional<MedicalCenter> optionalMedicalCenter = medicalCenterRepository.findById(id);
        if (optionalMedicalCenter.isPresent()) {
            medicalCenterRepository.deleteById(id);
        } else {
            throw new AppException("Medical center not found", HttpStatus.BAD_REQUEST);
        }
    }
}
