package com.project.MedicalRegister.Controller;

import com.project.MedicalRegister.DTO.InvestigationDto;
import com.project.MedicalRegister.Model.Investigation;
import com.project.MedicalRegister.Service.InvestigationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/investigations")
public class InvestigationController {

    private final InvestigationService investigationService;

    @PostMapping("/create")
    public ResponseEntity<String> createInvestigation (@RequestBody InvestigationDto investigationDto) {
        Investigation createdInvestigation = investigationService.addInvestigationToDoctor(investigationDto);

        return ResponseEntity.ok("Investigation added successfully with ID: " + createdInvestigation.getInvestigationId());
    }

    @GetMapping("/readAll")
    public ResponseEntity<Iterable<Investigation>> readInvestigationsByDoctorId (@RequestParam("doctorId")  Long doctorId) {
        Iterable<Investigation> investigations = investigationService.getInvestigationsByDoctorId(doctorId);

        return ResponseEntity.ok(investigations);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateInvestigation (@PathVariable("id") Long id, @RequestBody InvestigationDto investigationDto) {
        investigationService.updateInvestigation(id, investigationDto);

        return ResponseEntity.ok("Investigation updated successfully with ID: " + id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteInvestigation (@PathVariable("id") Long id) {
        investigationService.deleteInvestigation(id);

        return ResponseEntity.ok("Investigation deleted successfully with ID: " + id);
    }

}
