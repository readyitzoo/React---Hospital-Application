package com.project.MedicalRegister.Controller;

import com.project.MedicalRegister.DTO.ReceptionistDto;
import com.project.MedicalRegister.DTO.UserDto;
import com.project.MedicalRegister.Service.ReceptionistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/receptionist")
public class ReceptionistController {

    private final ReceptionistService receptionistService;

    // adminul il poate crea
    @CrossOrigin ("http://localhost:3000")
    @PostMapping("/create")
    public ResponseEntity<String> createReceptionist(@RequestBody ReceptionistDto receptionistDto) throws Exception {
        UserDto createdUser = receptionistService.addReceptionist(receptionistDto);

        return ResponseEntity.ok("User created successfully with ID: " + createdUser.getUser_id());
    }
    @CrossOrigin ("http://localhost:3000")
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateReceptionist(@PathVariable("id") Long id, @RequestBody ReceptionistDto receptionistDto) throws Exception {
        //TODO: verific daca este receptionist
        System.out.println("id receptionist to be updated: " + id);
        receptionistService.updateReceptionist(id, receptionistDto);
        return ResponseEntity.ok("Receptionist updated successfully with ID: " + id);
    }
    @CrossOrigin ("http://localhost:3000")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteReceptionist(@PathVariable("id") Long id) {
        receptionistService.deleteReceptionist(id);
        return ResponseEntity.ok("Receptionist deleted successfully with ID:) " + id);
    }
    @CrossOrigin ("http://localhost:3000")
    @GetMapping("/read/{id}")
    public Object getReceptionist(@PathVariable("id") Long id) throws Exception {
        Object receptionistDto = receptionistService.getReceptionist(id);
        return ResponseEntity.ok(receptionistDto);
    }
    @CrossOrigin ("http://localhost:3000")
    @GetMapping("/readAllByHospital")
    public Object getAllByHospital(@RequestParam("hospitalName") String hospitalName) throws Exception {
        List<Object> receptionistsDto = receptionistService.getAllByHospitalName(hospitalName);
        return ResponseEntity.ok(receptionistsDto);
    }

}
