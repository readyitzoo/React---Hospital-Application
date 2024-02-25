package com.project.MedicalRegister.Controller;

import com.project.MedicalRegister.DTO.DoctorDto;
import com.project.MedicalRegister.DTO.UserDto;
import com.project.MedicalRegister.Service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
@CrossOrigin ("http://localhost:3000")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/doctor")
public class DoctorController {

    private final DoctorService doctorService;

    // hospital adminul il poate crea
    @CrossOrigin ("http://localhost:3000")
    @PostMapping("/create")
    public ResponseEntity<String> createDoctor(@RequestBody DoctorDto doctor) throws Exception {
        UserDto createdUser = doctorService.addDoctor(doctor);

        return ResponseEntity.ok("User created successfully with ID: " + createdUser.getUser_id());
    }

    @CrossOrigin ("http://localhost:3000")
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateDoctor(@PathVariable("id") Long id, @RequestBody DoctorDto doctorDto) throws Exception {
        doctorService.updateDoctor(id, doctorDto);
        return ResponseEntity.ok("Doctor updated successfully with ID: " + id);
    }

    @CrossOrigin ("http://localhost:3000")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteDoctor(@PathVariable("id") Long id) {
        doctorService.deleteDoctor(id);
        return ResponseEntity.ok("Doctor deleted successfully with ID:) " + id);
    }

    @CrossOrigin ("http://localhost:3000")
    @GetMapping("/read/{id}")
    public Object getDoctor(@PathVariable("id") Long id) throws Exception {
        DoctorDto doctorDto = doctorService.getDoctor(id);
        Map<String, Object> response = Map.of("userId", id,"doctorInfo", doctorDto);
        return ResponseEntity.ok(response);
    }

    @CrossOrigin ("http://localhost:3000")
    @GetMapping("/readAll")
    public Object getAllDoctors() throws Exception {
        List<Object> doctorDtos = doctorService.getAllDoctors();
        return ResponseEntity.ok(doctorDtos);
    }
    @CrossOrigin ("http://localhost:3000")
    @GetMapping("/readAllByHospital")
    public Object getAllDoctorsByHospital(@RequestParam("hospitalName") String hospitalName) throws Exception {
        List<Object> doctorDtos = doctorService.getAllDoctorsByHospital(hospitalName);
        return ResponseEntity.ok(doctorDtos);
    }
    @CrossOrigin ("http://localhost:3000")
    @GetMapping("/readAllBySpecialization")
    public Object getAllDoctorsBySpecialization(@RequestParam("specializationName") String specializationName) throws Exception {
        List<Object> doctorDtos = doctorService.getAllDoctorsBySpecialization(specializationName);
        return ResponseEntity.ok(doctorDtos);
    }
}