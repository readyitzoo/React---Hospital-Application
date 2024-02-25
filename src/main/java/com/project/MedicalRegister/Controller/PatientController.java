package com.project.MedicalRegister.Controller;

import com.project.MedicalRegister.DTO.PatientDto;
import com.project.MedicalRegister.Service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/patient")
public class PatientController {

    private final PatientService patientService;

    @GetMapping("/read/{id}")
    public Object readPatientById (@PathVariable("id") Long id) throws Exception {
        Object patient = patientService.getPatientById(id);

        return ResponseEntity.ok(patient);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updatePatient (@PathVariable("id") Long id, @RequestBody PatientDto patientDto) throws Exception {
        patientService.updatePatient(id, patientDto);

        return ResponseEntity.ok("Patient updated successfully with ID: " + id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePatient (@PathVariable("id") Long id) {
        patientService.deletePatient(id);

        return ResponseEntity.ok("Patient deleted successfully with ID: " + id);
    }
}
