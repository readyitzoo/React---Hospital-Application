package com.project.MedicalRegister.Service;

import com.project.MedicalRegister.DTO.MedicalHistoryDto;
import com.project.MedicalRegister.Exceptions.AppException;
import com.project.MedicalRegister.Model.MedicalHistory;
import com.project.MedicalRegister.Repository.MedicalHistoryRepo;
import com.project.MedicalRegister.Util.Encryption;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MedicalHistoryService {

    private final MedicalHistoryRepo medicalHistoryRepo;

    @Transactional
    public MedicalHistory addMedicalHistory(MedicalHistoryDto medicalHistoryDto) throws Exception {
        MedicalHistory medicalHistory = new MedicalHistory();
        medicalHistory.setPatientId(medicalHistoryDto.getPatientId());
        medicalHistory.setDoctorId(medicalHistoryDto.getDoctorId());
        medicalHistory.setDiagnostic(Encryption.encrypt(medicalHistoryDto.getDiagnostic()));
        medicalHistory.setTreatment(Encryption.encrypt(medicalHistoryDto.getTreatment()));
        if(medicalHistoryDto.getFile() != null){
            medicalHistory.setFileName(medicalHistoryDto.getFile().getOriginalFilename());
            try {
                medicalHistory.setFileContent(medicalHistoryDto.getFile().getBytes());
            } catch (Exception e) {
                throw new AppException("Could not upload file", HttpStatus.BAD_REQUEST);
            }

        }

        MedicalHistory saved = medicalHistoryRepo.save(medicalHistory);
        return saved;
    }

    public MedicalHistory getMedicalHistoryById(Long id) throws Exception {
        Optional<MedicalHistory> opmedicalHistory = medicalHistoryRepo.findByMedicalHistoryId(id);
        if (opmedicalHistory.isEmpty()) {
            throw new AppException("No medical history found with ID: " + id, HttpStatus.BAD_REQUEST);
        }else {
            MedicalHistory medicalHistory  = opmedicalHistory.get();
            medicalHistory.setDiagnostic(Encryption.decrypt(medicalHistory.getDiagnostic()));
            medicalHistory.setTreatment(Encryption.decrypt(medicalHistory.getTreatment()));
            return medicalHistory;
        }
    }

    public Iterable<MedicalHistory> getMedicalHistoryByPatientId(Long patientId) {
        Optional<Iterable<MedicalHistory>> opmedicalHistories = medicalHistoryRepo.findAllByPatientId(patientId);
        if (opmedicalHistories.isEmpty()) {
            throw new AppException("No medical histories found for patient with ID: " + patientId, HttpStatus.BAD_REQUEST);
        }
        else {
            Iterable<MedicalHistory> medicalHistories = opmedicalHistories.get();
            medicalHistories.forEach(medicalHistory -> {
                try {
                    medicalHistory.setDiagnostic(Encryption.decrypt(medicalHistory.getDiagnostic()));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                try {
                    medicalHistory.setTreatment(Encryption.decrypt(medicalHistory.getTreatment()));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
            return medicalHistories;
        }
    }

    @Transactional
    public void updateMedicalHistory(Long id, MedicalHistoryDto medicalHistoryDto) {
        Optional<MedicalHistory> optionalMedicalHistory = medicalHistoryRepo.findByMedicalHistoryId(id);
        if (optionalMedicalHistory.isPresent()) {
            MedicalHistory medicalHistory = optionalMedicalHistory.get();
            medicalHistory.setPatientId(medicalHistoryDto.getPatientId());
            medicalHistory.setDoctorId(medicalHistoryDto.getDoctorId());
            medicalHistory.setDiagnostic(medicalHistoryDto.getDiagnostic());
            medicalHistory.setTreatment(medicalHistoryDto.getTreatment());
            if (medicalHistoryDto.getFile() != null) {
                medicalHistory.setFileName(medicalHistoryDto.getFile().getOriginalFilename());
                try {
                    medicalHistory.setFileContent(medicalHistoryDto.getFile().getBytes());
                } catch (Exception e) {
                    throw new AppException("Could not upload file", HttpStatus.BAD_REQUEST);
                }
            }
            medicalHistoryRepo.save(medicalHistory);
        } else {
            throw new AppException("Medical history not found", HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public void deleteMedicalHistory(Long id) {
        Optional<MedicalHistory> optionalMedicalHistory = medicalHistoryRepo.findByMedicalHistoryId(id);
        if (optionalMedicalHistory.isPresent()) {
            medicalHistoryRepo.deleteById(id);
        } else {
            throw new AppException("Medical history not found", HttpStatus.BAD_REQUEST);
        }
    }
}
