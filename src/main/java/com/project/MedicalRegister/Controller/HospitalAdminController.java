package com.project.MedicalRegister.Controller;

import com.project.MedicalRegister.DTO.HospitalAdminDto;
import com.project.MedicalRegister.DTO.UserDto;
import com.project.MedicalRegister.Service.HospitalAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/HospitalAdmin")
public class HospitalAdminController {

    private final HospitalAdminService hospitalAdminService;

    // adminul il poate crea
    @CrossOrigin ("http://localhost:3000")
    @PostMapping("/create")
    public ResponseEntity<String> createHospitalAdmin(@RequestBody HospitalAdminDto hospitalAdmin) throws Exception {
        UserDto createdUser = hospitalAdminService.addHospitalAdmin(hospitalAdmin);

        return ResponseEntity.ok("User created successfully with ID: " + createdUser.getUser_id());
    }
    @CrossOrigin ("http://localhost:3000")
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateHospitalAdmin(@PathVariable("id") Long id, @RequestBody HospitalAdminDto hospitalAdminDto) throws Exception {
        hospitalAdminService.updateHospitalAdmin(id, hospitalAdminDto);
        return ResponseEntity.ok("HospitalAdmin updated successfully with ID: " + id);
    }
    @CrossOrigin ("http://localhost:3000")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteHospitalAdmin(@PathVariable("id") Long id) {
        hospitalAdminService.deleteHospitalAdmin(id);
        return ResponseEntity.ok("HospitalAdmin deleted successfully with ID:) " + id);
    }
    @CrossOrigin ("http://localhost:3000")
    @GetMapping("/read/{id}")
    public Object getHospitalAdmin(@PathVariable("id") Long id) throws Exception {
        Object hospitalAdmin = hospitalAdminService.getHospitalAdmin(id);
        return ResponseEntity.ok(hospitalAdmin);
    }
    @CrossOrigin ("http://localhost:3000")
    @GetMapping("/readAll")
    public Object getAllHospitalAdmins() throws Exception {
        List<Object> hospitalAdminDtos = hospitalAdminService.getAllHospitalAdmins();
        return ResponseEntity.ok(hospitalAdminDtos);
    }

}
