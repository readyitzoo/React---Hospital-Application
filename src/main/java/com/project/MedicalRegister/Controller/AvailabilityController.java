package com.project.MedicalRegister.Controller;

import com.project.MedicalRegister.DTO.AvailabilityDto;
import com.project.MedicalRegister.Model.Availability;
import com.project.MedicalRegister.Model.WaitingApprovals;
import com.project.MedicalRegister.Service.AvailabilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin ("http://localhost:3000")

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/availability")
public class AvailabilityController {

    private final AvailabilityService availabilityService;
    @CrossOrigin ("http://localhost:3000")
    @PostMapping("/create")
    public ResponseEntity<String> createAvailability (@RequestBody List<AvailabilityDto> availabilityDtoList) {
        availabilityService.addAvailabilityForDoctor(availabilityDtoList);

        return ResponseEntity.ok("Work schedule added successfully");
    }
    @CrossOrigin ("http://localhost:3000")
    @PostMapping("/sendForApproval")
    public ResponseEntity<String> sendForApproval (@RequestBody List<AvailabilityDto> availabilityDtoList) {
        availabilityService.sendForApproval(availabilityDtoList);

        return ResponseEntity.ok("Work schedule sent for approval");
    }

    // in asteptare de aprobare
    @CrossOrigin ("http://localhost:3000")
    @GetMapping("/readAllForApproval")
    public ResponseEntity<Iterable<WaitingApprovals>> readAllWaitingForApproval (@RequestParam("hospitalAdminId") Long hospitalAdminId) {
        Iterable<WaitingApprovals> availabilities = availabilityService.getAllWaitingForApproval(hospitalAdminId);

        return ResponseEntity.ok(availabilities);
    }

    // id-ul este id-ul din tabelul waiting approvals
    @CrossOrigin ("http://localhost:3000")
    @PutMapping("/approve")
    public ResponseEntity<String> approveAvailability (@RequestParam("approveId") Long id) {
        availabilityService.updateAvailability(id);

        return ResponseEntity.ok("Work schedule updated successfully");
    }
    @CrossOrigin ("http://localhost:3000")
    @GetMapping("/read")
    public ResponseEntity<Iterable<Availability>> readAvailabilityByDoctorId (@RequestParam("doctorId") Long doctorId) {
        Iterable<Availability> availabilities = availabilityService.getAvailabilityByDoctorId(doctorId);

        return ResponseEntity.ok(availabilities);
    }

}
