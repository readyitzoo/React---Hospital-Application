package com.project.MedicalRegister.Controller;

import com.project.MedicalRegister.Model.Specialization;
import com.project.MedicalRegister.Service.SpecializationService;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/specialization")
public class SpecializationController {

    private final SpecializationService specializationService;
    @CrossOrigin ("http://localhost:3000")
    @PostMapping("/create")
    public ResponseEntity<String> createSpecialization(@RequestBody @NotEmpty String specializationName) {
        Specialization createdSpecialization = specializationService.addSpecialization(specializationName);

        return ResponseEntity.ok("Specialization created successfully with ID: " + createdSpecialization.getSpecializationId());
    }
    @CrossOrigin ("http://localhost:3000")
    @GetMapping("/readAll")
    public ResponseEntity<Iterable<Specialization>> getAllSpecializations() {
        Iterable<Specialization> specializations = specializationService.getAllSpecializations();
        return ResponseEntity.ok(specializations);
    }
    @CrossOrigin ("http://localhost:3000")
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateSpecialization(@PathVariable("id") Long id, @RequestBody @NotEmpty String specializationName) {
        specializationService.updateSpecialization(id, specializationName);
        return ResponseEntity.ok("Specialization updated successfully with ID: " + id);
    }
    @CrossOrigin ("http://localhost:3000")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteSpecialization(@PathVariable("id") Long id) {
        specializationService.deleteSpecialization(id);
        return ResponseEntity.ok("Specialization deleted successfully with ID: " + id);
    }


}
