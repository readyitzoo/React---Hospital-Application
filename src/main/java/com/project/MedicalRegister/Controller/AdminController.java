package com.project.MedicalRegister.Controller;

import com.project.MedicalRegister.DTO.AdminDto;
import com.project.MedicalRegister.DTO.HospitalAdminDto;
import com.project.MedicalRegister.DTO.UserDto;
import com.project.MedicalRegister.Service.AdminService;
import com.project.MedicalRegister.Service.HospitalAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    // EP tehnic
    @PostMapping("/create")
    public ResponseEntity<String> createHospitalAdmin(@RequestBody AdminDto adminDto) throws Exception {
        UserDto createdUser = adminService.create(adminDto);

        return ResponseEntity.ok("Admin created successfully with ID: " + createdUser.getUser_id());
    }
}
