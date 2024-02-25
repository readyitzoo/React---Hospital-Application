package com.project.MedicalRegister.Controller;

import com.project.MedicalRegister.DTO.MedicalCenterDto;
import com.project.MedicalRegister.Model.MedicalCenter;
import com.project.MedicalRegister.Service.MedicalCenterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/medicalCenter")
public class MedicalCenterController {

    private final MedicalCenterService medicalCenterService;
    @CrossOrigin ("http://localhost:3000")
    @PostMapping("/create")
    public ResponseEntity<String> createMedicalCenter(@RequestBody MedicalCenterDto medicalCenterDto) {
        MedicalCenter createdMedicalCenter = medicalCenterService.addMedicalCenter(medicalCenterDto);

        return ResponseEntity.ok("Medical center created successfully with ID: " + createdMedicalCenter.getMedicalCenterId());
    }
    @CrossOrigin ("http://localhost:3000")
    @GetMapping("/readAll")
    public ResponseEntity<Iterable<MedicalCenter>> getAllMedicalCenters() {
        Iterable<MedicalCenter> medicalCenters = medicalCenterService.getAllMedicalCenters();
        return ResponseEntity.ok(medicalCenters);
    }
    @CrossOrigin ("http://localhost:3000")
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateMedicalCenter(@PathVariable("id") Long id, @RequestBody MedicalCenterDto medicalCenterDto) {
        medicalCenterService.updateMedicalCenter(id, medicalCenterDto);
        return ResponseEntity.ok("Medical center updated successfully with ID: " + id);
    }
    @CrossOrigin ("http://localhost:3000")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteMedicalCenter(@PathVariable("id") Long id) {
        medicalCenterService.deleteMedicalCenter(id);
        return ResponseEntity.ok("Medical center deleted successfully with ID: " + id);
    }





}
